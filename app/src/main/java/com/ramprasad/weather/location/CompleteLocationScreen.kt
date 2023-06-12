package com.ramprasad.weather.location

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import com.google.android.gms.tasks.CancellationTokenSource
import com.ramprasad.weather.view.PermissionBox
import com.ramprasad.weather.viewModel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Ramprasad on 6/12/23.
 */
@SuppressLint("MissingPermission")
@Composable
fun CurrentLocationScreen(viewModel: WeatherViewModel, geocoder: Geocoder) {
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first()),
        onGranted = {
            CurrentLocationContent(
                usePreciseLocation = it.contains(Manifest.permission.ACCESS_FINE_LOCATION),
                viewModel,
                geocoder
            )
        },
    )
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
@Suppress("DEPRECATION")
fun CurrentLocationContent(
    usePreciseLocation: Boolean,
    weatherViewModel: WeatherViewModel,
    geocoder: Geocoder
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var locationInfo by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        //To get more accurate or fresher device location use this method
        scope.launch(Dispatchers.Default) {
            val priority = if (usePreciseLocation) {
                Priority.PRIORITY_HIGH_ACCURACY
            } else {
                Priority.PRIORITY_BALANCED_POWER_ACCURACY
            }
            val result = locationClient.getCurrentLocation(
                priority,
                CancellationTokenSource().token,
            ).await()
            result?.let { fetchedLocation ->
                locationInfo =
                    "Current location is \n" + "lat : ${fetchedLocation.latitude}\n" +
                            "long : ${fetchedLocation.longitude}\n" + "fetched at ${System.currentTimeMillis()}"

                scope.launch {

                    val addresses: List<Address> = geocoder.getFromLocation(
                        fetchedLocation.latitude,
                        fetchedLocation.longitude,
                        1
                    ) as List<Address>
                    weatherViewModel.getWeatherReport(addresses.first().locality)
                    weatherViewModel.getForecastWeather(
                        lat = fetchedLocation.latitude,
                        long = fetchedLocation.longitude
                    )
                }
            }
        }
    }
}