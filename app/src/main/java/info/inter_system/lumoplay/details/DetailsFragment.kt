package info.inter_system.lumoplay.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class DetailsFragment : Fragment() {
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DetailsScreen(
                    uiState = viewModel.uiState,
                    onUiEvent = viewModel::onUiEvent,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.connect()
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnect()
    }
}
