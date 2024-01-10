package com.solar.domain.model

data class SolarStatus(
    val batteryPower: Double? = 0.0,
    val batteryPowerStr: String? = "",
    val batteryPercent: Double? = 0.0,
    val familyLoadPower: Double? = 0.0,
    val familyLoadPowerStr: String? = "",
    val generatorPower: Int? = 0,
    val generatorPowerStr: String? = "",
    val totalLoadPower: Double? = 0.0,
    val totalLoadPowerStr: String? = "",
    val gridPower: Double? = 0.0,
    val gridPowerStr: String? = "",
    val solarPanelPower: Double? = 0.0,
    val solarPanelPowerStr: String? = "",
    val capacity: Double? = 0.0
)