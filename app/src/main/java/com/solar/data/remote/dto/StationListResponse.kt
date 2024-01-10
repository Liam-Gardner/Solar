package com.solar.data.remote.dto

data class StationListResponse(
    val code: String,
    val data: Data,
    val msg: String,
    val success: Boolean
)