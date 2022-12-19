package info.inter_system.lumoplay.model

sealed class Command(open val code: UByte) {
    open class Operation(override val code: UByte) : Command(code) {
        object Read : Operation(0x06u)
        object Write : Operation(0x03u)
    }

    object Terminator : Command(0xFEu)

    open class Section(override val code: UByte) : Command(code) {
        object Clock : Section(0x01u)
        object Music : Section(0x02u)
        object Light : Section(0x03u)
        object Stats : Section(0x04u)
        object Jingle : Section(0x06u)
    }

    open class Music(override val code: UByte) : Command(code) {
        object UserSelectedSong : Music(0x01u)
        object MasterSelectedDaySong : Music(0x02u)
        object MasterSelectedNightSong : Music(0x03u)
        object UserVolume : Music(0x04u)
        object MasterVolumeDay : Music(0x05u)
        object MasterVolumeNight : Music(0x06u)
    }

    open class Clock(override val code: UByte) : Command(code) {
        object Time : Clock(0x01u)
        object Date : Clock(0x02u)
        object NightTimeStart : Clock(0x03u)
        object NightTimeEnd : Clock(0x04u)
    }

    open class Light(override val code: UByte) : Command(code) {
        object UserLightMotive : Light(0x01u)
        object MasterLightMotive : Light(0x02u)
        object UserBrightness : Light(0x03u)
        object MasterBrightnessDay : Light(0x04u)
        object MasterBrightnessNight : Light(0x05u)
    }

    open class Stats(override val code: UByte) : Command(code) {
        object DaysInUse : Stats(0x01u)
        object Velocity : Stats(0x02u)
        object Voltage : Stats(0x03u)
        object NumberOfSpinsToday : Stats(0x12u)
        object NumberOfSpinsThisMonth : Stats(0x13u)
        object NumberOfSpinsTotal : Stats(0x14u)
        object TimeInUseToday : Stats(0x21u)
        object TimeInUseThisMonth : Stats(0x22u)
        object TimeInUseTotal : Stats(0x23u)
    }

    open class Jingle(override val code: UByte) : Command(code) {
        object Status : Jingle(0x02u)
    }
}
