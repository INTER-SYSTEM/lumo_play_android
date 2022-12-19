package info.inter_system.lumoplay.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import info.inter_system.lumoplay.model.*
import info.inter_system.lumoplay.R
import info.inter_system.lumoplay.ui.theme.CarouselGrey


private val strings = object : Any() {
    @Composable
    fun user(): String = stringResource(id = R.string.user)

    @Composable
    fun admin(): String = stringResource(id = R.string.admin)

    @Composable
    fun stats(): String = stringResource(id = R.string.stats)

    @Composable
    fun details(): String = stringResource(id = R.string.details)
}

@Composable
fun DetailsScreen(
    uiState: UiState,
    onUiEvent: (UiEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize()
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            contentColor = CarouselGrey,
            elevation = 0.dp,
            title = { },
            navigationIcon = {
                IconButton(
                    onClick = { onUiEvent(UiEvent.BackClick) },
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
        )
        uiState.viewState.BuildUI(
            uiState = uiState,
            onUiEvent = onUiEvent,
        )
    }
}
