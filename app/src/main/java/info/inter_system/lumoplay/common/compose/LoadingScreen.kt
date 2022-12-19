package info.inter_system.lumoplay.common.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(text: String) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
            )
        }

    }
}