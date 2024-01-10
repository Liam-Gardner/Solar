package com.solar.data.remote.dto

data class Page(
    val current: Int,
    val optimizeCountSql: Boolean,
    val orders: List<Any>,
    val pages: Int,
    val records: List<Record>,
    val searchCount: Boolean,
    val size: Int,
    val total: Int
)