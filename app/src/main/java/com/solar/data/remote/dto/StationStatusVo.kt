package com.solar.data.remote.dto

data class StationStatusVo(
    val all: Int,
    val building: Int,
    val fault: Int,
    val mppt: Int,
    val normal: Int,
    val offline: Int
)