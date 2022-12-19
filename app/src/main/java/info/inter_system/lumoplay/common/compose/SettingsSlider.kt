package info.inter_system.lumoplay.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.inter_system.lumoplay.common.roundToUInt
import info.inter_system.lumoplay.model.Command
import info.inter_system.lumoplay.model.WriteState
import info.inter_system.lumoplay.ui.theme.Red20

@Composable
fun SettingsSlider(
    commandPattern: Command,
    writeState: WriteState? = null,
    text: String,
    value: UInt,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    enabled: Boolean = true,
    onUpdate: (Command, UInt) -> Unit,
    color: Color = Color.Gray,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.background(if (writeState is WriteState.Writing && writeState.command == commandPattern) Red20 else Color.Transparent)
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
            text = text,
            color = color
        )

        Slider(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(4.dp),
            value = value.toFloat(),
            onValueChange = { onUpdate(commandPattern, it.roundToUInt()) },
            valueRange = valueRange,
            steps = steps,
            enabled = enabled,
            colors = SliderDefaults.colors(
                thumbColor = color,
            )
        )
    }
}