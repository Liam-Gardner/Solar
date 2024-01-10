package com.solar.presentation

sealed class Screen(val route: String) {
    data object SolarScreen : Screen("stationDetail_screen")
    data object OpenPhoneScreen : Screen("openPhone_screen")
}
