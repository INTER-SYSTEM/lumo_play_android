package info.inter_system.lumoplay.permissions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.inter_system.lumoplay.common.NavigationService

class PermissionsViewModel : ViewModel() {
    var viewState by mutableStateOf<PermissionsViewState>(PermissionsViewState.Loading)
        private set

    val requestFinish: MutableLiveData<Long> = MutableLiveData()
    val requestPermissions: MutableLiveData<Long> = MutableLiveData()

    val navigationService = NavigationService.instance

    fun permissionsAreGranted() {
        navigationService.navigateToScan()
    }

    fun permissionsAreDenied() {
        viewState = PermissionsViewState.NoPermissions
    }

    fun rationaleRequired() {
        viewState = PermissionsViewState.Rationale
    }

    fun onFinishClick() {
        requestFinish.value = System.currentTimeMillis()
    }

    fun onGetPermissionsClick() {
        requestPermissions.value = System.currentTimeMillis()
    }
}