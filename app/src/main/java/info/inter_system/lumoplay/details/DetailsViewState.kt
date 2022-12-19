package info.inter_system.lumoplay.details

import androidx.compose.runtime.Composable
import info.inter_system.lumoplay.model.*

sealed class DetailsViewState {
    @Composable
    abstract fun BuildUI(
        uiState: UiState,
        onUiEvent: (UiEvent) -> Unit,
    )

    object Admin : DetailsViewState() {
        @Composable
        override fun BuildUI(
            uiState: UiState,
            onUiEvent: (UiEvent) -> Unit,
        ) {
            AdminScreen(
                uiState = uiState,
                onUiEvent = onUiEvent,
            )
        }
    }

    object User : DetailsViewState() {
        @Composable
        override fun BuildUI(
            uiState: UiState,
            onUiEvent: (UiEvent) -> Unit,
        ) {
            UserScreen(
                uiState = uiState,
                onUiEvent = onUiEvent,
            )
        }
    }

    object Pin : DetailsViewState() {
        @Composable
        override fun BuildUI(
            uiState: UiState,
            onUiEvent: (UiEvent) -> Unit,

        ) {
            PinScreen(
                uiState = uiState,
                onUiEvent = onUiEvent,
            )
        }

    }
}
