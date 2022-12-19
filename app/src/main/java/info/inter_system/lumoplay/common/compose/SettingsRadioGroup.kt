package info.inter_system.lumoplay.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
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
fun SettingsRadioGroup(
    items: List<Pair<UInt, String>>,
    commandPattern: Command,
    writeState: WriteState,
    value: UInt,
    enabled: Boolean = true,
    onUpdate: (Command, UInt) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (writeState is WriteState.Writing && writeState.command == commandPattern) Red20 else Color.Transparent)
    ) {
        items.forEach { (idx, title) ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (idx == value),
                        onClick = { onUpdate(commandPattern, idx) }
                    )
                    .padding(horizontal = 4.dp)
            ) {
                RadioButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    selected = (idx == value),
                    enabled = enabled,
                    onClick = { onUpdate(commandPattern, idx) }
                )
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }

        }
    }

}