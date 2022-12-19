package info.inter_system.lumoplay.common.compose

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
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
import info.inter_system.lumoplay.common.secondUByte
import info.inter_system.lumoplay.common.thirdUByte
import info.inter_system.lumoplay.common.toUInt
import info.inter_system.lumoplay.model.Command
import info.inter_system.lumoplay.model.WriteState
import info.inter_system.lumoplay.ui.theme.Red20
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun SettingsDateAndTime(
    commandDatePattern: Command,
    commandTimePattern: Command,
    writeState: WriteState,
    onUpdate: (Command, UInt) -> Unit,
    text: String,
    timeValue: UInt,
    dateValue: UInt,
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
            onUpdate(commandTimePattern, newTimeArr.toUInt())
        },
        hour,
        minute,
        true
    ).apply { updateTime(timeValue.thirdUByte().toInt(), timeValue.fourthUByte().toInt()) }

    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateString = String.format("20%02d-%02d-%02d", dateValue.fourthUByte().toInt(), dateValue.thirdUByte().toInt(), dateValue.secondUByte().toInt())
    var date = LocalDate.now()
    try {
        date = LocalDate.parse(dateString, dateFormat)
    } catch (e: DateTimeParseException) {
        Log.w("SettingsDateAndTime", "Parsing went wrong", e)
    }
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val day = calendar[Calendar.DAY_OF_MONTH]
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            val newDateArr = ubyteArrayOf(d.toUByte(), m.toUByte(), (year - 2000).toUByte()).asByteArray()
            onUpdate(commandDatePattern, newDateArr.toUInt())
        },
        year,
        month,
        day
    ). apply { updateDate(date.year, date.monthValue, date.dayOfMonth) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (writeState is WriteState.Writing && (writeState.command == commandDatePattern || writeState.command == commandTimePattern)) Red20 else Color.Transparent)
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
                text = String.format("%02d:%02d", timeValue.thirdUByte().toInt(), timeValue.fourthUByte().toInt())
            )
        }
        Button(
            modifier = Modifier
                .padding(4.dp),
            onClick = { datePickerDialog.show() },
            enabled = enabled
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = "${date.dayOfMonth} ${date.month} ${date.year}"
            )
        }
    }
}
