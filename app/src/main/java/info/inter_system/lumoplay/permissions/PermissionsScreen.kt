package info.inter_system.lumoplay.permissions

import androidx.compose.runtime.Composable

@Composable
fun PermissionsScreen(
    viewState: PermissionsViewState,
    onFinishClick: () -> Unit,
    onGetPermissionsClick: () -> Unit,
) {
    viewState.BuildUI(
        onFinishClick,
        onGetPermissionsClick,
    )
}
