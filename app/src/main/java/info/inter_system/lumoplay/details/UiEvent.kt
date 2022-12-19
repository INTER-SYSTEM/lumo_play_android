package info.inter_system.lumoplay.details

import info.inter_system.lumoplay.model.Command

sealed class UiEvent {
    object BackClick : UiEvent()
    data class Update(val command: Command, val value: UInt) : UiEvent()
    object SettingsClick : UiEvent()
    data class PinChange(val value: String) : UiEvent()
    object PinOkClick : UiEvent()
    object PinCancelClick : UiEvent()
}
