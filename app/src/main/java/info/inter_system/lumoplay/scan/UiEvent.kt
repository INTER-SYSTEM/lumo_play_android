package info.inter_system.lumoplay.scan

import com.polidea.rxandroidble3.scan.ScanResult

sealed class UiEvent {
    object Refresh : UiEvent()
    data class DeviceClick(val scanResult: ScanResult) : UiEvent()
}
