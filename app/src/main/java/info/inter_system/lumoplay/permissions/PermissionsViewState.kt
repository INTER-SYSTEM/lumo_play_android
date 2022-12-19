package info.inter_system.lumoplay.permissions

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import info.inter_system.lumoplay.R
import info.inter_system.lumoplay.common.compose.LoadingScreen

private val strings = object : Any() {
    @Composable
    fun obtainingPermissions(): String = stringResource(id = R.string.obtaining_permissions)

    @Composable
    fun rationaleExplanation(): String = stringResource(id = R.string.rationale_explanation)

    @Composable
    fun obtainPermissionsBtn(): String = stringResource(id = R.string.ask_permissions_btn)

    @Composable
    fun closeAppBtn(): String = stringResource(id = R.string.close_app_btn)

    @Composable
    fun noPermissions(): String = stringResource(id = R.string.no_permissions)
}

sealed class PermissionsViewState {
    @Composable
    abstract fun BuildUI(
        onFinishClick: () -> Unit,
        onGetPermissionsClick: () -> Unit,
    )

    object Loading : PermissionsViewState() {
        @Composable
        override fun BuildUI(
            onFinishClick: () -> Unit,
            onGetPermissionsClick: () -> Unit,
        ) {
            LoadingScreen(text = strings.obtainingPermissions())
        }
    }

    object Rationale : PermissionsViewState() {
        @Composable
        override fun BuildUI(
            onFinishClick: () -> Unit,
            onGetPermissionsClick: () -> Unit,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = strings.rationaleExplanation(),
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(8.dp),
                        onClick = onGetPermissionsClick
                    ) {
                        Text(
                            text = strings.obtainPermissionsBtn()
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(8.dp),
                        onClick = onFinishClick
                    ) {
                        Text(
                            text = strings.closeAppBtn()
                        )
                    }
                }
            }
        }
    }

    object NoPermissions : PermissionsViewState() {
        @Composable
        override fun BuildUI(
            onFinishClick: () -> Unit,
            onGetPermissionsClick: () -> Unit,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = strings.noPermissions(),
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(8.dp),
                        onClick = onFinishClick
                    ) {
                        Text(
                            text = strings.closeAppBtn()
                        )
                    }
                }
            }
        }
    }
}