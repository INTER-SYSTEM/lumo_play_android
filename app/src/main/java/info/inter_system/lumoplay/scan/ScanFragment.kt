package info.inter_system.lumoplay.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class ScanFragment : Fragment() {
    private val viewModel by viewModels<ScanViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        subscribeFinishEvent()
        return ComposeView(requireContext()).apply {
            setContent {
                ScanScreen(
                    uiState = viewModel.uiState,
                    onUiEvent = viewModel::onUiEvent,
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopScan()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startScan()
    }

    private fun subscribeFinishEvent() =
        viewModel.finish
            .observe(viewLifecycleOwner) { finish ->
                if (finish != null && finish) {
                    requireActivity().finishAndRemoveTask()
                }
            }
}
