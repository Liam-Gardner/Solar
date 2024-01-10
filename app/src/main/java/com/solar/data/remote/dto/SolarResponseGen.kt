package com.solar.data.remote.dto

data class SolarResponseGen(
    val code: String,
    val data: SolarData,
    val msg: String,
    val success: Boolean
)