package info.inter_system.lumoplay.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import info.inter_system.lumoplay.model.AdminSettings
import info.inter_system.lumoplay.model.UserSettings
import info.inter_system.lumoplay.model.WriteState

class UiState {
    var viewState by mutableStateOf<DetailsViewState>(DetailsViewState.User)
    var userSettings by mutableStateOf(UserSettings.Empty)
    var adminSettings by mutableStateOf(AdminSettings.Empty)
    var writeState by mutableStateOf<WriteState>(WriteState.NoWriting)
    var isLoading by mutableStateOf(true)
    var pin by mutableStateOf("")
}
