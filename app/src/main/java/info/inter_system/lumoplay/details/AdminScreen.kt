package info.inter_system.lumoplay.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.inter_system.lumoplay.R
import info.inter_system.lumoplay.common.compose.*
import info.inter_system.lumoplay.model.AdminSettings
import info.inter_system.lumoplay.model.Command
import info.inter_system.lumoplay.model.WriteState

private val strings = object : Any() {
    @Composable
    fun melodyList(): List<Pair<UInt, String>> = listOf(
        0u to "BLE AUDIO",
        1u to "${stringResource(id = R.string.melody)} 1",
        2u to "${stringResource(id = R.string.melody)} 2",
        3u to "${stringResource(id = R.string.melody)} 3",
        4u to "${stringResource(id = R.string.melody)} 4",
        5u to "${stringResource(id = R.string.melody)} 5",
        6u to "${stringResource(id = R.string.melody)} 6",
        7u to "${stringResource(id = R.string.melody)} 7",
        8u to "${stringResource(id = R.string.melody)} 8",
        9u to "${stringResource(id = R.string.melody)} 9",
        10u to "${stringResource(id = R.string.melody)} 10",
    )

    @Composable
    fun lightMotiveList(): List<Pair<UInt, String>> = listOf(
        1u to "${stringResource(id = R.string.motive)} 1",
        2u to "${stringResource(id = R.string.motive)} 2",
        3u to "${stringResource(id = R.string.motive)} 3",
        4u to "${stringResource(id = R.string.motive)} 4",
        5u to "${stringResource(id = R.string.motive)} 5",
    )

    @Composable
    fun adminSettings(): String = stringResource(id = R.string.admin_settings)

    @Composable
    fun lightMotive(): String = stringResource(id = R.string.light_motive)

    @Composable
    fun volume(num: UInt): String = stringResource(id = R.string.volume) + " ($num)"

    @Composable
    fun audioDay(): String = stringResource(id = R.string.audio_day)

    @Composable
    fun audioNight(): String = stringResource(id = R.string.audio_night)

    @Composable
    fun brightnessDay(num: UInt): String = stringResource(id = R.string.brightness_day) + " ($num)"

    @Composable
    fun brightnessNight(num: UInt): String = stringResource(id = R.string.brightness_night) + " ($num)"

    @Composable
    fun clock(): String = stringResource(id = R.string.clock)

    @Composable
    fun dateTime(): String = stringResource(id = R.string.date_time)

    @Composable
    fun nightBegin(): String = stringResource(id = R.string.night_begin)

    @Composable
    fun nightEnd(): String = stringResource(id = R.string.night_end)

    @Composable
    fun jingle(): String = stringResource(id = R.string.jingle)
}

@Composable
fun AdminScreen(
    uiState: UiState,
    onUiEvent: (UiEvent) -> Unit,
) {
    val isEnabled = uiState.writeState == WriteState.NoWriting
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = strings.adminSettings(),
            )

            SettingsSubtitle(strings.audioDay())
            SettingsSection {
                SettingsRadioGroup(
                    items = strings.melodyList(),
                    commandPattern = Command.Music.MasterSelectedDaySong,
                    writeState = uiState.writeState,
                    value = uiState.adminSettings.daySong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    enabled = isEnabled
                )
                SettingsSlider(
                    commandPattern = Command.Music.MasterVolumeDay,
                    writeState = uiState.writeState,
                    text = strings.volume(uiState.adminSettings.dayVolume),
                    value = uiState.adminSettings.dayVolume,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    steps = 1,
                    valueRange = 1f..3f,
                    enabled = isEnabled
                )
            }

            SettingsSubtitle(strings.audioNight())
            SettingsSection {
                SettingsRadioGroup(
                    items = strings.melodyList(),
                    commandPattern = Command.Music.MasterSelectedNightSong,
                    writeState = uiState.writeState,
                    value = uiState.adminSettings.nightSong,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    enabled = isEnabled
                )
                SettingsSlider(
                    commandPattern = Command.Music.MasterVolumeNight,
                    writeState = uiState.writeState,
                    text = strings.volume(uiState.adminSettings.nightVolume),
                    value = uiState.adminSettings.nightVolume,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    steps = 1,
                    valueRange = 1f..3f,
                    enabled = isEnabled
                )
            }

            SettingsSubtitle(strings.lightMotive())
            SettingsSection {
                SettingsRadioGroup(
                    items = strings.lightMotiveList(),
                    commandPattern = Command.Light.MasterLightMotive,
                    writeState = uiState.writeState,
                    value = uiState.adminSettings.lightMotive,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    enabled = isEnabled,
                )
                SettingsSlider(
                    commandPattern = Command.Light.MasterBrightnessDay,
                    writeState = uiState.writeState,
                    text = strings.brightnessDay(uiState.adminSettings.dayBrightness),
                    value = uiState.adminSettings.dayBrightness,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    steps = 1,
                    valueRange = 1f..3f,
                    enabled = isEnabled
                )
                SettingsSlider(
                    commandPattern = Command.Light.MasterBrightnessNight,
                    writeState = uiState.writeState,
                    text = strings.brightnessNight(uiState.adminSettings.nightBrightness),
                    value = uiState.adminSettings.nightBrightness,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    steps = 1,
                    valueRange = 1f..3f,
                    enabled = isEnabled
                )
            }

            SettingsSubtitle(strings.jingle())
            SettingsSection {
                SettingsSwitch(
                    commandPattern = Command.Jingle.Status,
                    writeState = uiState.writeState,
                    text = strings.jingle(),
                    value = uiState.adminSettings.jingle,
                    enabled = isEnabled,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                )
            }

            SettingsSubtitle(strings.clock())
            SettingsSection {
                SettingsDateAndTime(
                    commandDatePattern = Command.Clock.Date,
                    commandTimePattern = Command.Clock.Time,
                    writeState = uiState.writeState,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    text = strings.dateTime(),
                    timeValue = uiState.adminSettings.time,
                    dateValue = uiState.adminSettings.date,
                    enabled = isEnabled,
                )
                SettingsTime(
                    commandPattern = Command.Clock.NightTimeStart,
                    writeState = uiState.writeState,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    text = strings.nightBegin(),
                    value = uiState.adminSettings.nightTimeStart,
                    enabled = isEnabled,
                )
                SettingsTime(
                    commandPattern = Command.Clock.NightTimeEnd,
                    writeState = uiState.writeState,
                    onUpdate = { c, v -> onUiEvent(UiEvent.Update(c, v)) },
                    text = strings.nightEnd(),
                    value = uiState.adminSettings.nightTimeEnd,
                    enabled = isEnabled,
                )
            }
        }
    }
}

