package info.inter_system.lumoplay.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.inter_system.lumoplay.common.BluetoothService
import info.inter_system.lumoplay.common.NavigationService
import info.inter_system.lumoplay.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    val uiState = UiState()

    private val navigationService = NavigationService.instance
    private val bluetoothService = BluetoothService.instance
    private var connectionJob: Job? = null
    private var wasConnected = false

    init {
        viewModelScope.launch {
            bluetoothService.connected.collect { connected ->
                if (connected) {
                    wasConnected = true
                    changeViewState(uiState.viewState)
                }

                if (wasConnected && connected.not()) {
                    onBackClick()
                }
            }
        }
        uiState.pin = PIN
    }

    private fun changeViewState(newViewState: DetailsViewState) {
        uiState.viewState = newViewState
        viewModelScope.launch {
            uiState.isLoading = true
            try {
                when (newViewState) {
                    DetailsViewState.User -> {
                        uiState.viewState = DetailsViewState.User
                        uiState.userSettings = bluetoothService.readUserSettings()
                        Log.d(TAG, "${uiState.userSettings}")
                    }
                    DetailsViewState.Admin -> {
                        uiState.viewState = DetailsViewState.Admin
                        uiState.adminSettings = bluetoothService.readAdminSettings()
                        Log.d(TAG, "${uiState.adminSettings}")
                    }
                    DetailsViewState.Pin -> {
                        uiState.viewState = DetailsViewState.Pin
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Something went wrong", e)
                onBackClick()
            } finally {
                uiState.isLoading = false
            }
        }
    }

    fun connect() {
        navigationService.systemBackHandler = { onBackClick() }
        connectionJob = viewModelScope.launch {
            bluetoothService.connect()
        }
    }

    fun disconnect() {
        navigationService.systemBackHandler = null
        connectionJob?.cancel()
    }

    private fun onBackClick() {
        if (uiState.viewState == DetailsViewState.User) {
            navigationService.goBack()
        } else {
            changeViewState(DetailsViewState.User)
        }
    }

    private fun onUpdate(command: Command, value: UInt) {
        if (uiState.writeState is WriteState.Writing) return

        viewModelScope.launch {
            when (uiState.viewState) {
                DetailsViewState.User -> {
                    uiState.writeState = WriteState.Writing(command)
                    uiState.userSettings = bluetoothService.writeUserSettings(uiState.userSettings, command, value)
                    uiState.writeState = WriteState.NoWriting
                    Log.e(TAG, "user settings: ${uiState.userSettings}")
                }
                DetailsViewState.Admin -> {
                    uiState.writeState = WriteState.Writing(command)
                    uiState.adminSettings = bluetoothService.writeAdminSettings(uiState.adminSettings, command, value)
                    uiState.writeState = WriteState.NoWriting
                    Log.e(TAG, "admin settings: ${uiState.adminSettings}")
                }
                else -> Unit
            }
        }
    }

    private fun onPinChange(newPin: String) {
        uiState.pin = newPin
    }

    private fun onPinOkClick() {
        if (uiState.pin.contentEquals(PIN)) {
            changeViewState(DetailsViewState.Admin)
        }
        //uiState.pin = ""
    }

    private fun onPinCancelClick() {
        //uiState.pin = ""
        onBackClick()
    }

    fun onUiEvent(event: UiEvent) {
        when (event) {
            UiEvent.BackClick -> onBackClick()
            is UiEvent.Update -> onUpdate(event.command, event.value)
            UiEvent.SettingsClick -> changeViewState(DetailsViewState.Pin)
            is UiEvent.PinChange -> onPinChange(event.value)
            UiEvent.PinCancelClick -> onPinCancelClick()
            UiEvent.PinOkClick -> onPinOkClick()
        }
    }

    private companion object {
        const val TAG = "DetailsViewModel"
        const val PIN = "qwerty123456"
    }
}
