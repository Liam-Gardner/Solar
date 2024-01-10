package com.solar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.dialog.Confirmation
import com.google.android.gms.base.R
import com.solar.presentation.station_detail.StationDetailViewModel


@Composable
fun OpenPhoneScreen(
    navController: NavHostController,
    viewModel: StationDetailViewModel = hiltViewModel(),
) {
    Confirmation(
        onTimeout = {
            viewModel.setLoadingFalse()
            navController.popBackStack()
        },
        modifier = Modifier.fillMaxSize(),
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.common_full_open_on_phone),
                contentDescription = null,
                tint = LocalContentColor.current
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Open on phone",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

            }
        }
    )
}