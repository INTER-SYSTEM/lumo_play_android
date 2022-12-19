package info.inter_system.lumoplay.details

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import info.inter_system.lumoplay.R
import info.inter_system.lumoplay.model.Command
import info.inter_system.lumoplay.model.UserSettings
import info.inter_system.lumoplay.model.WriteState
import info.inter_system.lumoplay.ui.theme.Blue
import info.inter_system.lumoplay.ui.theme.CarouselGrey
import info.inter_system.lumoplay.ui.theme.Orange
import java.lang.Integer.max
import kotlin.math.cos
import kotlin.math.sin

private val strings = object : Any() {
    @Composable
    fun volume(): String = stringResource(id = R.string.volume)

    @Composable
    fun brightness(): String = stringResource(id = R.string.brightness)
}

@Composable
fun UserScreen(
    uiState: UiState,
    onUiEvent: (UiEvent) -> Unit,
) {
    val isEnabled = uiState.writeState == WriteState.NoWriting
    val alpha: Float by animateFloatAsState(if (!uiState.isLoading) 1f else 0f, tween())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.6f)
        ) {
            Carousel(isLoading = uiState.isLoading)

            LightsSelector(
                modifier = Modifier
                    .align(Alignment.Center),
                circleRadius = { 0.85f }
            ) {
                SectionLightItem(
                    alpha = alpha,
                    item = 1u,
                    selected = uiState.userSettings.lightMotive,
                    commandPattern = Command.Light.UserLightMotive,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionLightItem(
                    alpha = alpha,
                    item = 2u,
                    selected = uiState.userSettings.lightMotive,
                    commandPattern = Command.Light.UserLightMotive,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionLightItem(
                    alpha = alpha,
                    item = 3u,
                    selected = uiState.userSettings.lightMotive,
                    commandPattern = Command.Light.UserLightMotive,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionLightItem(
                    alpha = alpha,
                    item = 4u,
                    selected = uiState.userSettings.lightMotive,
                    commandPattern = Command.Light.UserLightMotive,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionLightItem(
                    alpha = alpha,
                    item = 5u,
                    selected = uiState.userSettings.lightMotive,
                    commandPattern = Command.Light.UserLightMotive,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )
            }

            MelodySelector(
                modifier = Modifier
                    .align(Alignment.Center),
                circleRadius = { 0.85f }
            ) {
                SectionMelodyItem(
                    alpha = alpha,
                    item = 1u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 2u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 3u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 4u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 5u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 6u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 7u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 8u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 9u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )

                SectionMelodyItem(
                    alpha = alpha,
                    item = 10u,
                    selected = uiState.userSettings.song,
                    commandPattern = Command.Music.UserSelectedSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    isEnabled = isEnabled,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 16.dp,
                    )
                    .graphicsLayer(alpha = alpha)
            ) {
                Text(
                    modifier = Modifier
                        .padding(6.dp)
                        .weight(5f)
                        .align(Alignment.CenterVertically),
                    text = strings.volume(),
                    textAlign = TextAlign.End,
                    color = Blue,
                )
                SectionVectorItem(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    alpha = alpha,
                    color = Blue,
                    item = 0u,
                    selected = uiState.userSettings.volume,
                    commandPattern = Command.Music.UserVolume,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    vector = ImageVector.vectorResource(id = R.drawable.ic_volume_mute),
                    isEnabled = isEnabled,
                    size = 42.dp
                )
                Spacer(modifier = Modifier.weight(1f))
                SectionVectorItem(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    alpha = alpha,
                    color = Blue,
                    item = 1u,
                    selected = uiState.userSettings.volume,
                    commandPattern = Command.Music.UserVolume,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    vector = ImageVector.vectorResource(id = R.drawable.ic_volume_down),
                    isEnabled = isEnabled,
                    size = 50.dp
                )
                Spacer(modifier = Modifier.weight(1f))
                SectionVectorItem(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    alpha = alpha,
                    color = Blue,
                    item = 2u,
                    selected = uiState.userSettings.volume,
                    commandPattern = Command.Music.UserVolume,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    vector = ImageVector.vectorResource(id = R.drawable.ic_volume_up),
                    isEnabled = isEnabled,
                    size = 58.dp
                )
                Spacer(modifier = Modifier.weight(5f))
            }
        }

        Box(
            modifier = Modifier
                .graphicsLayer(
                    alpha = alpha
                )
                .fillMaxSize()
                .weight(0.4f)
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(0.8f)
                    .align(Alignment.TopCenter),
                painter = painterResource(id = R.drawable.dj),
                contentDescription = null
            )

            SectionVectorItem(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(32.dp),
                alpha = alpha,
                color = Blue,
                item = 0u,
                selected = uiState.userSettings.song,
                commandPattern = Command.Music.UserSelectedSong,
                onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                vector = ImageVector.vectorResource(id = R.drawable.ic_bluetooth_audio),
                isEnabled = isEnabled,
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp)
                    .size(48.dp)
                    .clickable { onUiEvent(UiEvent.SettingsClick) },
                tint = CarouselGrey,
                imageVector = ImageVector.vectorResource(R.drawable.ic_settings),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun BoxScope.Carousel(
    isLoading: Boolean,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angleAnimation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing)
        )
    )
    var angle by remember { mutableStateOf(0f) }
    if (isLoading) {
        angle = angleAnimation
    }

    Image(
        modifier = Modifier
            .fillMaxWidth(0.55f)
            .align(Alignment.Center)
            .graphicsLayer { rotationZ = angle },
        painter = painterResource(id = R.drawable.carousel),
        contentDescription = null,
    )
}

@Composable
fun LightsSelector(
    modifier: Modifier,
    contentPadding: Dp = 0.dp,
    circleRadius: () -> Float,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier = modifier) { children, constraints ->
        //val screenWidth = configuration.screenWidthDp.dp.toPx()*  0.5 * circleRadius()
        val screenWidth = constraints.maxWidth * 0.5 * circleRadius()

        val placeables = children.map { it.measure(constraints) }

        val maxItemHeight = placeables.maxOf {
            it.height
        }
        val maxItemWidth = placeables.maxOf {
            it.width
        }

        val gap = 100 / placeables.size
        val radiusOffset = (max(maxItemHeight, maxItemWidth) / 2) + contentPadding.toPx()
        val radius = screenWidth - radiusOffset
        val offset = 140 - gap / 2f

        layout(screenWidth.toInt(), screenWidth.toInt()) {
            for (i in placeables.indices) {
                val radians = Math.toRadians((offset - (gap * i)).toDouble())
                placeables[i].placeRelative(
                    x = (cos(radians) * radius + screenWidth / 2).toInt() - placeables[i].width / 2,
                    y = (sin(radians) * radius + 0).toInt() + 48.dp.roundToPx() + placeables[i].height / 2,
                )
            }
        }
    }
}

@Composable
fun MelodySelector(
    modifier: Modifier,
    contentPadding: Dp = 0.dp,
    circleRadius: () -> Float,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier = modifier) { children, constraints ->
        //val screenWidth = configuration.screenWidthDp.dp.toPx()*  0.5 * circleRadius()
        val screenWidth = constraints.maxWidth * 0.5 * circleRadius()

        val placeables = children.map { it.measure(constraints) }

        val maxItemHeight = placeables.maxOf {
            it.height
        }
        val maxItemWidth = placeables.maxOf {
            it.width
        }

        val gap = 210 / placeables.size
        val radiusOffset = (max(maxItemHeight, maxItemWidth) / 2) + contentPadding.toPx()
        val radius = screenWidth - radiusOffset
        val offset = 15 - gap / 2f

        layout(screenWidth.toInt(), screenWidth.toInt()) {
            for (i in placeables.indices) {
                val radians = Math.toRadians((offset - (gap * i)).toDouble())
                val j = placeables.size - 1 - i
                placeables[j].placeRelative(
                    x = (cos(radians) * radius + screenWidth / 2).toInt() - placeables[j].width / 2,
                    y = (sin(radians) * radius + 0).toInt() + 48.dp.roundToPx() + placeables[j].height / 2,
                )
            }
        }
    }
}

@Composable
fun SectionLightItem(
    alpha: Float,
    commandPattern: Command,
    selected: UInt,
    item: UInt,
    onUpdate: (Command, UInt) -> Unit,
    isEnabled: Boolean,
) {
    val isSelected = item == selected
    Box(modifier = Modifier
        .clickable { if (isEnabled) onUpdate(commandPattern, item) }
        .graphicsLayer(
            alpha = alpha
        )
        .size(40.dp)
        .padding(if (isSelected) 0.dp else 5.dp)
        .background(
            color = if (isSelected) Orange else Color.White,
            shape = CircleShape
        )
        .border(
            width = if (isSelected) 0.dp else 2.dp,
            color = Orange,
            shape = CircleShape
        )
    ) {
        if (isSelected) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_light_mode),
                contentDescription = null,
                tint = Color.White
            )
        } else {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = item.toString(),
                color = Orange,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SectionMelodyItem(
    alpha: Float,
    commandPattern: Command,
    selected: UInt,
    item: UInt,
    onUpdate: (Command, UInt) -> Unit,
    isEnabled: Boolean,
) {
    val isSelected = item == selected
    Box(modifier = Modifier
        .clickable { if (isEnabled) onUpdate(commandPattern, item) }
        .graphicsLayer(
            alpha = alpha
        )
        .size(40.dp)
        .padding(if (isSelected) 0.dp else 5.dp)
        .background(
            color = if (isSelected) Blue else Color.White,
            shape = CircleShape
        )
        .border(
            width = if (isSelected) 0.dp else 2.dp,
            color = Blue,
            shape = CircleShape
        )
    ) {
        if (isSelected) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "\uD834\uDD1E",
                color = Color.White
            )
        } else {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = item.toString(),
                color = Blue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SectionVectorItem(
    modifier: Modifier = Modifier,
    alpha: Float,
    color: Color,
    item: UInt,
    selected: UInt,
    commandPattern: Command,
    onUpdate: (Command, UInt) -> Unit,
    vector: ImageVector,
    isEnabled: Boolean,
    size: Dp = 40.dp
) {
    val isSelected = item == selected
    Box(modifier = modifier
        .clickable { if (isEnabled) onUpdate(commandPattern, item) }
        .graphicsLayer(
            alpha = alpha
        )
        .size(size)
        .background(
            color = if (isSelected) color else Color.White,
            shape = CircleShape
        )
        .border(
            width = if (isSelected) 0.dp else 2.dp,
            color = color,
            shape = CircleShape
        )
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = vector,
            contentDescription = null,
            tint = if (isSelected) Color.White else color,
        )
    }
}

@Preview
@Composable
fun UserScreenPreview() {
    UserScreen(UiState().apply {
        isLoading = false
        userSettings = UserSettings(
            song = 2u,
            volume = 0u,
            lightMotive = 2u,
            brightness = 1u,
        )

    }) { }
}

