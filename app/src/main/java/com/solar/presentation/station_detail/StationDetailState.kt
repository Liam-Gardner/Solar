package com.solar.presentation.station_detail

import com.solar.domain.model.SolarStatus

data class StationDetailState(
    val error: String = "",
    val solarState: SolarStatus? = SolarStatus(),
    val isLoading: Boolean = false
)
