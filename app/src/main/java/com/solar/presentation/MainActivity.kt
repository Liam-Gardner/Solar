/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.solar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.solar.presentation.station_detail.StationDetailScreen
import com.solar.presentation.theme.SolarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolarApp()
        }
    }
}

@Composable
fun SolarApp() {
    SolarTheme {
        Scaffold {
            val navController = rememberSwipeDismissableNavController()
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = Screen.SolarScreen.route
            ) {
                // TODO: new screen that checks if api key is available. If no, nav to input screen (to be created) if yes, nav to Station Details Screen
                composable(route = Screen.SolarScreen.route) {
                    StationDetailScreen(navController = navController)
                }
                composable(route = Screen.OpenPhoneScreen.route) {
                    OpenPhoneScreen(navController = navController)
                }
            }
        }

    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SolarApp()
}