package info.inter_system.lumoplay.common

import android.app.Application
import android.os.ParcelUuid
import android.util.Log
import com.polidea.rxandroidble3.*
import com.polidea.rxandroidble3.exceptions.BleCharacteristicNotFoundException
import com.polidea.rxandroidble3.exceptions.BleDisconnectedException
import com.polidea.rxandroidble3.scan.ScanFilter
import com.polidea.rxandroidble3.scan.ScanResult
import com.polidea.rxandroidble3.scan.ScanSettings
import info.inter_system.lumoplay.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.rx3.asFlow
import kotlinx.coroutines.rx3.await
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalUnsignedTypes::class)
class BluetoothService(application: Application) {
    private val client = RxBleClient.create(application)
    private var device: RxBleDevice? = null
    private val RxBleDevice.isConnected: Boolean
        get() = connectionState == RxBleConnection.RxBleConnectionState.CONNECTED
    private var connection: RxBleConnection? = null
    private var connectionMonitoringJob: Job? = null
    private var indicationJob: Job? = null
    private val responseChannel = Channel<BTResponse>()
    private val mutex = Mutex()
    private var timeoutJob: Job? = null

    val connected = MutableStateFlow(false)

    init {
        RxBleClient.updateLogOptions(
            LogOptions.Builder()
                .setLogLevel(LogConstants.VERBOSE)
                .setMacAddressLogSetting(LogConstants.MAC_ADDRESS_FULL)
                .setUuidsLogSetting(LogConstants.UUIDS_FULL)
                .setShouldLogAttributeValues(true)
                .build()
        )
    }

    fun startScan(): Flow<ScanResult> {
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        val scanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid(serviceUUID))
            .build()

        return client.scanBleDevices(scanSettings, scanFilter).asFlow()
    }

    fun selectDevice(scanResult: ScanResult) {
        device = scanResult.bleDevice
        connectionMonitoringJob?.cancel()
        connectionMonitoringJob = btScope.launch {
            try {
                device?.observeConnectionStateChanges()?.asFlow()?.collect {
                    if (device?.isConnected == true) {
                        delay(INITIALISATION_DELAY_IN_MILLISECONDS) // delay needed to fully initialize connection, seems that event is too fast
                        connected.emit(true)
                    } else {
                        connected.emit(false)
                    }
                }
            } catch (e: BleDisconnectedException) {
                Log.w(TAG, "Device disconnected", e)
            }
        }
    }

    @OptIn(FlowPreview::class)
    suspend fun connect() {
        try {
            device?.establishConnection(true, Timeout(5, TimeUnit.SECONDS))?.asFlow()?.collect {
                connection = it
                indicationJob?.cancel()
                indicationJob = btScope.launch {
                    try {
                        connection?.setupIndication(characteristicUUID)
                            ?.asFlow()
                            ?.flatMapConcat { it.asFlow() }
                            ?.collect { rawResponse ->
                                if (rawResponse.size < MIN_RESPONSE_SIZE) {
                                    Log.w(TAG, "Response ${rawResponse.toHex()} is too short. Ignoring")
                                } else {
                                    Log.d(TAG, "response = ${rawResponse.toHex()}")

                                    responseChannel.send(
                                        BTResponse(
                                            raw = rawResponse,
                                            command = rawResponse.takeCommand(),
                                            value = rawResponse.takeResponse(),
                                        )
                                    )
                                }
                            }
                    } catch (e: BleDisconnectedException) {
                        Log.w(TAG, "Device disconnected", e)
                    } catch (e: BleCharacteristicNotFoundException) {
                        Log.w(TAG, "Cannot find characteristic", e)
                    }
                }
            }
        }catch (e: BleDisconnectedException) {
            Log.w(TAG, "Device disconnected", e)
        }
    }

    suspend fun readAdminSettings(): AdminSettings {
        return AdminSettings(
            daySong = read(Command.Music.MasterSelectedDaySong).toUInt(),
            nightSong = read(Command.Music.MasterSelectedNightSong).toUInt(),
            dayVolume = read(Command.Music.MasterVolumeDay).toUInt(),
            nightVolume = read(Command.Music.MasterVolumeNight).toUInt(),
            lightMotive = read(Command.Light.MasterLightMotive).toUInt(),
            dayBrightness = read(Command.Light.MasterBrightnessDay).toUInt(),
            nightBrightness = read(Command.Light.MasterBrightnessNight).toUInt(),
            time = read(Command.Clock.Time).toUInt(),
            date = read(Command.Clock.Date).toUInt(),
            nightTimeStart = read(Command.Clock.NightTimeStart).toUInt(),
            nightTimeEnd = read(Command.Clock.NightTimeEnd).toUInt(),
            jingle = read(Command.Jingle.Status).toUInt(),
        )
    }

    suspend fun writeAdminSettings(
        adminSettings: AdminSettings,
        command: Command,
        value: UInt
    ): AdminSettings = adminSettings.copy(
        daySong = writeAdminSettingsField(adminSettings, Command.Music.MasterSelectedDaySong, command, value),
        nightSong = writeAdminSettingsField(adminSettings, Command.Music.MasterSelectedNightSong, command, value),
        dayVolume = writeAdminSettingsField(adminSettings, Command.Music.MasterVolumeDay, command, value),
        nightVolume = writeAdminSettingsField(adminSettings, Command.Music.MasterVolumeNight, command, value),
        lightMotive = writeAdminSettingsField(adminSettings, Command.Light.MasterLightMotive, command, value),
        dayBrightness = writeAdminSettingsField(adminSettings, Command.Light.MasterBrightnessDay, command, value),
        nightBrightness = writeAdminSettingsField(adminSettings, Command.Light.MasterBrightnessNight, command, value),
        time = writeAdminSettingsField(adminSettings, Command.Clock.Time, command, value),
        date = writeAdminSettingsField(adminSettings, Command.Clock.Date, command, value),
        nightTimeStart = writeAdminSettingsField(adminSettings, Command.Clock.NightTimeStart, command, value),
        nightTimeEnd = writeAdminSettingsField(adminSettings, Command.Clock.NightTimeEnd, command, value),
        jingle = writeAdminSettingsField(adminSettings, Command.Jingle.Status, command, value),
    )

    private suspend fun writeAdminSettingsField(
        adminSettings: AdminSettings,
        patternCommand: Command,
        command: Command,
        value: UInt,
    ): UInt = when {
        patternCommand != command && patternCommand == Command.Music.MasterSelectedDaySong -> adminSettings.daySong
        patternCommand != command && patternCommand == Command.Music.MasterSelectedNightSong -> adminSettings.nightSong
        patternCommand != command && patternCommand == Command.Music.MasterVolumeDay -> adminSettings.dayVolume
        patternCommand != command && patternCommand == Command.Music.MasterVolumeNight -> adminSettings.nightVolume
        patternCommand != command && patternCommand == Command.Light.MasterLightMotive -> adminSettings.lightMotive
        patternCommand != command && patternCommand == Command.Light.MasterBrightnessDay -> adminSettings.dayBrightness
        patternCommand != command && patternCommand == Command.Light.MasterBrightnessNight -> adminSettings.nightBrightness
        patternCommand != command && patternCommand == Command.Clock.Time -> adminSettings.time
        patternCommand != command && patternCommand == Command.Clock.Date -> adminSettings.date
        patternCommand != command && patternCommand == Command.Clock.NightTimeStart -> adminSettings.nightTimeStart
        patternCommand != command && patternCommand == Command.Clock.NightTimeEnd -> adminSettings.nightTimeEnd
        patternCommand != command && patternCommand == Command.Jingle.Status -> adminSettings.jingle
        patternCommand == command && patternCommand == Command.Clock.Time -> write(command, listOf(value.thirdUByte(), value.fourthUByte())).toUInt()
        patternCommand == command && patternCommand == Command.Clock.Date -> write(command, listOf(value.secondUByte(), value.thirdUByte(), value.fourthUByte())).toUInt()
        patternCommand == command && patternCommand == Command.Clock.NightTimeStart -> write(command, listOf(value.thirdUByte(), value.fourthUByte())).toUInt()
        patternCommand == command && patternCommand == Command.Clock.NightTimeEnd -> write(command, listOf(value.thirdUByte(), value.fourthUByte())).toUInt()
        patternCommand == command -> write(command, listOf(value.toUByte())).toUInt()
        else -> 0u
    }


    suspend fun readUserSettings(): UserSettings {
        return UserSettings(
            song = read(Command.Music.UserSelectedSong).toUInt(),
            volume = read(Command.Music.UserVolume).toUInt(),
            lightMotive = read(Command.Light.UserLightMotive).toUInt(),
            brightness = read(Command.Light.UserBrightness).toUInt(),
        )
    }

    suspend fun writeUserSettings(
        userSettings: UserSettings,
        command: Command,
        value: UInt
    ): UserSettings = userSettings.copy(
        song = writeUserSettingsField(userSettings, Command.Music.UserSelectedSong, command, value),
        volume = writeUserSettingsField(userSettings, Command.Music.UserVolume, command, value),
        lightMotive = writeUserSettingsField(userSettings, Command.Light.UserLightMotive, command, value),
        brightness = writeUserSettingsField(userSettings, Command.Light.UserBrightness, command, value),
    )

    private suspend fun writeUserSettingsField(
        userSettings: UserSettings,
        patternCommand: Command,
        command: Command,
        value: UInt,
    ): UInt = when {
        patternCommand != command && patternCommand == Command.Music.UserSelectedSong -> userSettings.song
        patternCommand != command && patternCommand == Command.Music.UserVolume -> userSettings.volume
        patternCommand != command && patternCommand == Command.Light.UserLightMotive -> userSettings.lightMotive
        patternCommand != command && patternCommand == Command.Light.UserBrightness -> userSettings.brightness
        patternCommand == command -> write(command, listOf(value.toUByte())).toUInt()
        else -> 0u
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun write(command: Command, value: List<UByte>): ByteArray {
        mutex.withLock {
            var response: BTResponse

            do {
                if (responseChannel.isEmpty.not()) {
                    responseChannel.receive()
                }

                timeoutJob = doTimeoutJob()
                connection?.writeCharacteristic(
                    characteristicUUID,
                    writeCommandArray(command.getSection(), command, value)
                )?.await()
                response = responseChannel.receive()
                timeoutJob?.cancel()
            }  while (response.raw.contentEquals(RESPONSE_TIMEOUT_CODE))

            return response.value
        }
    }

    suspend fun readStatistics(): Statistics {
        return Statistics(
            daysInUse = read(Command.Stats.DaysInUse).toUInt(),
            velocity = read(Command.Stats.Velocity).toUInt(),
            voltage = read(Command.Stats.Voltage).toUInt(),
            numberOfSpinsToday = read(Command.Stats.NumberOfSpinsToday).toUInt(),
            numberOfSpinsThisMonth = read(Command.Stats.NumberOfSpinsThisMonth).toUInt(),
            numberOfSpinsTotal = read(Command.Stats.NumberOfSpinsTotal).toUInt(),
            timeInUseToday = read(Command.Stats.TimeInUseToday).toUInt(),
            timeInUseThisMonth = read(Command.Stats.TimeInUseThisMonth).toUInt(),
            timeInUseTotal = read(Command.Stats.TimeInUseTotal).toUInt(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun read(command: Command): ByteArray {
        mutex.withLock {
            var response: BTResponse

            do {
                if (responseChannel.isEmpty.not()) {
                    responseChannel.receive()
                }

                timeoutJob = doTimeoutJob()
                connection?.writeCharacteristic(
                    characteristicUUID,
                    readCommandArray(command.getSection(), command)
                )?.await()
                response = responseChannel.receive()
                timeoutJob?.cancel()
            } while (response.raw.contentEquals(RESPONSE_TIMEOUT_CODE))

            return response.value
        }
    }

    private fun doTimeoutJob(): Job = btScope.launch {
        delay(RESPONSE_DELAY_IN_MILLISECONDS)
        responseChannel.send(BTResponse(RESPONSE_TIMEOUT_CODE))
    }

    private fun writeCommandArray(section: Command.Section, command: Command, value: List<UByte>): ByteArray {
        val arr = UByteArray(value.size + 4)
        arr[0] = Command.Operation.Write.code
        arr[1] = section.code
        arr[2] = command.code
        value.forEachIndexed { index, uByte -> arr[index + 3] = uByte }
        arr[arr.size -1] = Command.Terminator.code
        return arr.asByteArray().apply { Log.d(TAG, "write command: ${this.toHex()}") }
    }

    private fun readCommandArray(section: Command.Section, command: Command): ByteArray =
        ubyteArrayOf(
            Command.Operation.Read.code,
            section.code,
            command.code,
            Command.Terminator.code
        ).asByteArray().apply { Log.d(TAG, "read command: ${this.toHex()}") }

    companion object {
        private const val TAG = "BluetoothService"
        private const val SERVICE_UUID = "0000fff0-0000-1000-8000-00805f9b34fb"
        private const val CHARACTERISTIC_UUID = "0000fff1-0000-1000-8000-00805f9b34fb"
        private const val INITIALISATION_DELAY_IN_MILLISECONDS = 1200L
        private val RESPONSE_TIMEOUT_CODE: ByteArray = byteArrayOfInts(0xDE, 0xAD, 0xFE, 0xED)
        private const val RESPONSE_DELAY_IN_MILLISECONDS = 600L
        private const val MIN_RESPONSE_SIZE = 5

        private val serviceUUID: UUID
            get() = UUID.fromString(SERVICE_UUID)

        private val characteristicUUID: UUID
            get() = UUID.fromString(CHARACTERISTIC_UUID)

        private val btScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        lateinit var instance: BluetoothService

        fun create(application: Application) {
            instance = BluetoothService(application)
        }

        private fun Command.getSection(): Command.Section = when(this) {
            is Command.Music -> Command.Section.Music
            is Command.Clock -> Command.Section.Clock
            is Command.Light -> Command.Section.Light
            is Command.Jingle -> Command.Section.Jingle
            else -> Command.Section.Stats
        }
    }
}
