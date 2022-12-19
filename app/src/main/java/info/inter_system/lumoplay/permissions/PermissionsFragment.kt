package info.inter_system.lumoplay.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class PermissionsFragment : Fragment() {
    private val viewModel by viewModels<PermissionsViewModel>()
    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.values.none { false }) {
                viewModel.permissionsAreGranted()
            } else {
                if (shouldShowRequestPermissionRationale(SCAN_PERMISSION)
                    || shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_CONNECT)
                ) {
                    viewModel.rationaleRequired()
                } else {
                    viewModel.permissionsAreDenied()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PermissionsScreen(
                    viewState = viewModel.viewState,
                    onFinishClick = viewModel::onFinishClick,
                    onGetPermissionsClick = viewModel::onGetPermissionsClick,
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.requestFinish.observe(viewLifecycleOwner) {
            requireActivity().finish()
        }

        viewModel.requestPermissions.observe(viewLifecycleOwner) {
            requestPermissions()
        }

        checkPermissions()
    }

    private fun checkPermissions() = when {
        checkScanPermission() == PackageManager.PERMISSION_GRANTED
                && checkConnectPermission() == PackageManager.PERMISSION_GRANTED -> {
            viewModel.permissionsAreGranted()
        }

        shouldShowRequestPermissionRationale(SCAN_PERMISSION)
                || shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_CONNECT) -> {
            viewModel.rationaleRequired()
        }

        else -> {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        permissionsLauncher.launch(arrayOf(SCAN_PERMISSION, Manifest.permission.BLUETOOTH_CONNECT))
    }

    private fun checkScanPermission() =
        ContextCompat.checkSelfPermission(requireContext(), SCAN_PERMISSION)

    private fun checkConnectPermission() =
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT)

    private companion object {
        val SCAN_PERMISSION: String =
            if (Build.VERSION.SDK_INT >= 31) Manifest.permission.BLUETOOTH_SCAN else Manifest.permission.ACCESS_FINE_LOCATION
    }
}
