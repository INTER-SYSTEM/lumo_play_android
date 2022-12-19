package info.inter_system.lumoplay.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import info.inter_system.lumoplay.R
import info.inter_system.lumoplay.model.Statistics

private val strings = object : Any() {
    @Composable
    fun daysInUse(num: UInt): String = stringResource(id = R.string.days_in_use) + num

    @Composable
    fun velocity(num: UInt): String = stringResource(id = R.string.velocity) + num

    @Composable
    fun voltage(num: UInt): String = stringResource(id = R.string.voltage) + num

    @Composable
    fun numOfSpinsToday(num: UInt): String = stringResource(id = R.string.num_of_spins_today) + num

    @Composable
    fun numOfSpinsMonth(num: UInt): String = stringResource(id = R.string.num_of_spins_month) + num

    @Composable
    fun numOfSpinsTotal(num: UInt): String = stringResource(id = R.string.num_of_spins_total) + num

    @Composable
    fun timeInUseToday(num: UInt): String = stringResource(id = R.string.time_in_use_today) + num

    @Composable
    fun timeInUseMonth(num: UInt): String = stringResource(id = R.string.time_in_use_month) + num

    @Composable
    fun timeInUseTotal(num: UInt): String = stringResource(id = R.string.time_in_use_total) + num
}

@Composable
fun StatsScreen(statistics: Statistics) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.daysInUse(statistics.daysInUse),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.velocity(statistics.velocity),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.voltage(statistics.voltage),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.numOfSpinsToday(statistics.numberOfSpinsToday),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.numOfSpinsMonth(statistics.numberOfSpinsThisMonth),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.numOfSpinsTotal(statistics.numberOfSpinsTotal),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.timeInUseToday(statistics.timeInUseToday),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.timeInUseMonth(statistics.timeInUseThisMonth),
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.timeInUseTotal(statistics.timeInUseTotal),
            )
        }
    }
}
