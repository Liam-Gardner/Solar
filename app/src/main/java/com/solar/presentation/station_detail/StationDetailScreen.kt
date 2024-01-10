package com.solar.presentation.station_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SolarPower
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import com.solar.R


data class Detail(val text: String, val color: Color, val progress: Float)


fun lightenColor(color: Color, factor: Float): Color {
    val hsl = FloatArray(3)
    android.graphics.Color.colorToHSV(color.toArgb(), hsl)
    hsl[2] += factor // increase lightness
    hsl[2] = Math.max(0f, Math.min(hsl[2], 1f)) // make sure lightness stays within [0,1] range
    return Color(android.graphics.Color.HSVToColor(hsl))
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StationDetailScreen(
    viewModel: StationDetailViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state = viewModel.state.value

    val ptrState: PullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { viewModel.getStationDetail() },
    )

    fun removeMinus(value: Double?): Double {
        val stringValue = value.toString()
        val withoutMinus = stringValue.replace("-", "")
        return withoutMinus.toDouble()
    }

    fun getProgress(progress: Double?, total: Double?): Float {
        if (progress != null && total != null) {
            val percentageAsDouble = progress / total
            return percentageAsDouble.toFloat()
        }
        return 0.0f
    }

    val details = listOf(
        Detail(
            text = "${removeMinus(state.solarState?.solarPanelPower)} ${state.solarState?.generatorPowerStr}",
            color = if ((state.solarState?.solarPanelPower
                    ?: 0.0) > 0
            ) Color.Yellow else Color.Gray,
            progress =
            if ((state.solarState?.solarPanelPower ?: 0.0) == 0.0) 1f
            else getProgress(
                progress = state.solarState?.solarPanelPower,
                total = state.solarState?.capacity
            )
        ),
        Detail(
            text = "${removeMinus(state.solarState?.gridPower)} ${state.solarState?.totalLoadPowerStr}",
            color = if ((state.solarState?.gridPower ?: 0.0) < 0) Color.Red
            else if (state.solarState?.gridPower == 0.0) Color.Gray
            else Color.Blue,
            progress = 1f
        ),
        Detail(
            text = "${state.solarState?.batteryPercent?.toInt()}%",
            color = if ((state.solarState?.batteryPower ?: 0.0) < 0) Color.Magenta
            else if (state.solarState?.batteryPower == 0.0) Color.Gray
            else Color.Green,
            progress = state.solarState?.batteryPercent!!.toFloat() / 100
        ),
        Detail(
            text = "${state.solarState?.totalLoadPower} ${state.solarState?.totalLoadPowerStr}",
            color = if ((state.solarState?.familyLoadPower ?: 0.0) <= 0) Color.Gray else Color(
                0xFFFFA500 // Orange
            ), progress = 1.0f
        )
    )

    Box(
        Modifier
            .pullRefresh(ptrState)
    ) {
        ManualGrid(details)
        PullRefreshIndicator(
            refreshing = state.isLoading,
            ptrState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ManualGrid(details: List<Detail>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PowerDetail(
                    IconType.Vector(Icons.Filled.SolarPower),
                    text = details[0].text,
                    color = details[0].color,
                    progress = details[0].progress
                )
                PowerDetail(
                    IconType.Paint(painterResource(id = R.drawable.power_grid)),
                    text = details[1].text,
                    color = details[1].color,
                    progress = details[1].progress
                )
            }
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PowerDetail(
                    IconType.Vector(Icons.Filled.BatteryChargingFull),
                    text = details[2].text,
                    color = details[2].color,
                    progress = details[2].progress
                )
                PowerDetail(
                    IconType.Vector(Icons.Filled.Home),
                    text = details[3].text,
                    color = details[3].color,
                    progress = details[3].progress
                )
            }
        }
    }
}

sealed class IconType {
    data class Vector(val imageVector: ImageVector) : IconType()
    data class Paint(val painter: Painter) : IconType()
}

@Composable
fun PowerDetail(icon: IconType, text: String, progress: Float, color: Color) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Center),
            strokeWidth = 2.dp,
//            trackColor = lightenColor(color, 0.5f),
            indicatorColor = color
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (icon) {
                is IconType.Vector -> {
                    Icon(
                        imageVector = icon.imageVector,
                        contentDescription = "",
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text, fontSize = 10.sp, color = Color.White
                    )
                }

                is IconType.Paint -> {
                    Icon(
                        painter = icon.painter,
                        contentDescription = "",
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text, fontSize = 10.sp, color = Color.White
                    )
                }
            }
        }
    }
}

