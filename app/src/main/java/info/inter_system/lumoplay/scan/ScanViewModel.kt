package info.inter_system.lumoplay.scan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polidea.rxandroidble3.exceptions.BleScanException
import com.polidea.rxandroidble3.scan.ScanResult
import info.inter_system.lumoplay.common.BluetoothService
import info.inter_system.lumoplay.common.NavigationService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScanViewModel : ViewModel() {
    val uiState = UiState()
    val finish = MutableLiveData<Boolean>().apply { value = false }
    private var scanJob: Job? = null

    private val bluetoothService = BluetoothService.instance
    private val navigationService = NavigationService.instance

    fun startScan() {
        navigationService.systemBackHandler = { onBackClick() }
        uiState.isRefreshing = true
        disableRefreshing()
        scanJob = viewModelScope.launch {
            try {
                bluetoothService.startScan().collect { scanResult ->
                    uiState.isRefreshing = false
                    uiState.devices.withIndex()
                        .firstOrNull { it.value.bleDevice == scanResult.bleDevice }
                        ?.let {
                            uiState.devices[it.index] = scanResult
                        }
                        ?: run {
                            uiState.devices.add(scanResult)
                        }
                }
            } catch (e: BleScanException) {
                Log.w(TAG, "Scanning error", e)
                stopScan()
            }
        }
    }

    fun stopScan() {
        uiState.isRefreshing = true
        scanJob?.cancel()
        uiState.devices.clear()
    }

    private fun onBackClick() {
        finish.postValue(true)
    }

    private fun disableRefreshing() {
        viewModelScope.launch {
            delay(5000)
            uiState.isRefreshing = false
        }
    }

    fun onUiEvent(event: UiEvent) {
        fun refresh() {
            stopScan()
            startScan()
        }

        fun onDeviceClick(scanResult: ScanResult) {
            navigationService.navigateToDetails(scanResult.bleDevice.name ?: "")
            bluetoothService.selectDevice(scanResult)
        }

        when (event) {
            UiEvent.Refresh -> refresh()
            is UiEvent.DeviceClick -> onDeviceClick(event.scanResult)
        }
    }

    private companion object {
        const val TAG = "ScanViewModel"
    }
}
