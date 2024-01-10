package com.solar.data.remote.dto

data class SysGridPrice(
    val price: Double? = 0.0,
    val refId: String? = "",
    val sellBuy: Int? = 0,
    val source: Int? = 0,
    val type: Int? = 0,
    val unit: String? = ""
)