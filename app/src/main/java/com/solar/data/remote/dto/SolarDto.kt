package com.solar.data.remote.dto

import com.solar.domain.model.SolarStatus

fun SolarData.toSolarStatus(): SolarStatus {
    return SolarStatus(
        batteryPower,
        batteryPowerStr,
        batteryPercent,
        familyLoadPower,
        familyLoadPowerStr,
        generatorPower,
        generatorPowerStr,
        totalLoadPower,
        totalLoadPowerStr,
        gridPower = psum,
        gridPowerStr = psumStr,
        solarPanelPower = power,
        solarPanelPowerStr = powerStr,
        capacity
    )
}