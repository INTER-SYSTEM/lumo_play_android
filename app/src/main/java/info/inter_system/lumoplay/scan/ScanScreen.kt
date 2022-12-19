package info.inter_system.lumoplay.scan

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import info.inter_system.lumoplay.R
import info.inter_system.lumoplay.ui.theme.Grey1

private val strings = object : Any() {
    @Composable
    fun scan(): String = stringResource(id = R.string.scan)

    @Composable
    fun devices(): String = stringResource(id = R.string.devices)

    @Composable
    fun unknown(): String = stringResource(id = R.string.unknown)
}

@Composable
fun ScanScreen(
    uiState: UiState,
    onUiEvent: (UiEvent) -> Unit,
) {
    SwipeRefresh(
        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
        state = rememberSwipeRefreshState(uiState.isRefreshing),
        onRefresh = { onUiEvent(UiEvent.Refresh) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.4f)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Let's go!!!",
                )
                Image(
                    modifier = Modifier
                        .size(96.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.lumoplay),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey1,
                    text = "Turn the carousel",
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.6f)
            ) {
                item {
                    if (uiState.devices.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 12.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            text = "Devices found:",
                        )
                    } else {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            text = "No devices found",
                        )
                    }
                }

                uiState.devices.forEach {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .padding(horizontal = 8.dp)
                                .border(
                                    border = BorderStroke(2.dp, Grey1),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    Color.White,
                                    RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)
                                )
                                .clickable { onUiEvent(UiEvent.DeviceClick(it)) },
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .align(Alignment.CenterVertically),
                                fontSize = 24.sp,
                                text = it.bleDevice.name ?: strings.unknown(),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                modifier = Modifier
                                    .height(48.dp)
                                    .padding(end = 16.dp)
                                    .align(Alignment.CenterVertically),
                                painter = painterResource(id = R.drawable.ic_carousel),
                                contentDescription = null
                            )
                        }
                    }
                }

            }
        }
    }
}
