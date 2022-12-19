package info.inter_system.lumoplay.scan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.polidea.rxandroidble3.scan.ScanResult

class UiState {
    val devices = mutableStateListOf<ScanResult>()
    var isRefreshing by mutableStateOf(false)
}