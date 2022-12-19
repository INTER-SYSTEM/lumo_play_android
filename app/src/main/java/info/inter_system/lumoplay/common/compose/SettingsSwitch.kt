package info.inter_system.lumoplay.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.inter_system.lumoplay.model.Command
import info.inter_system.lumoplay.model.WriteState
import info.inter_system.lumoplay.ui.theme.Red20

@Composable
fun SettingsSwitch(
    commandPattern: Command,
    writeState: WriteState,
    text: String,
    value: UInt,
    enabled: Boolean = true,
    onUpdate: (Command, UInt) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (writeState is WriteState.Writing && writeState.command == commandPattern) Red20 else Color.Transparent)
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
            text = text
        )

        Switch(
            enabled = enabled,
            checked = value != 0u,
            onCheckedChange = {
                onUpdate(commandPattern, if (it) 1u else 0u)
            }
        )
    }
}
