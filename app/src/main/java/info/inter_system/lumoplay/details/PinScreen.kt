package info.inter_system.lumoplay.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PinScreen(
    uiState: UiState,
    onUiEvent: (UiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            text = "Enter pin"
        )

        val softwareKeyboardController = LocalSoftwareKeyboardController.current

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = uiState.pin,
            onValueChange = { onUiEvent(UiEvent.PinChange(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(onDone = {
                softwareKeyboardController?.hide()
                onUiEvent(UiEvent.PinOkClick)
            })

            )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onUiEvent(UiEvent.PinOkClick) },
        ) {
            Text("OK")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onUiEvent(UiEvent.PinCancelClick) },
        ) {
            Text("Cancel")
        }
    }
}
