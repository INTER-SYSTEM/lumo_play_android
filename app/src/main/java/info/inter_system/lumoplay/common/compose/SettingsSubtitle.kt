package info.inter_system.lumoplay.common.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsSubtitle(text: String) {
    Text(
        modifier = Modifier.padding(8.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        text = text,
    )
}