package info.inter_system.lumoplay.common.compose

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import info.inter_system.lumoplay.common.fourthUByte
import info.inter_system.lumoplay.common.thirdUByte
import info.inter_system.lumoplay.common.toUInt
import info.inter_system.lumoplay.model.Command
import info.inter_system.lumoplay.model.WriteState
import info.inter_system.lumoplay.ui.theme.Red20
import java.util.*

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun SettingsTime(
    commandPattern: Command,
    writeState: WriteState,
    onUpdate: (Command, UInt) -> Unit,
    text: String,
    value: UInt,
    enabled: Boolean = true,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, h: Int, m: Int ->
            val newTimeArr = ubyteArrayOf(h.toUByte(), m.toUByte()).asByteArray()
            onUpdate(commandPattern, newTimeArr.toUInt())
        },
        hour,
        minute,
        true
    ).apply { updateTime(value.thirdUByte().toInt(), value.fourthUByte().toInt()) }
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
        Button(
            modifier = Modifier
                .padding(4.dp),
            onClick = { timePickerDialog.show() },
            enabled = enabled
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = String.format("%02d:%02d", value.thirdUByte().toInt(), value.fourthUByte().toInt())
            )
        }
    }
}
