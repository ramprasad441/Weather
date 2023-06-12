package com.ramprasad.weather

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramprasad.weather.location.CurrentLocationScreen

import com.ramprasad.weather.view.ForecastWeatherCard
import com.ramprasad.weather.view.WeatherReportCard
import com.ramprasad.weather.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val geocoder = Geocoder(this, Locale.getDefault())
            CurrentLocationScreen(weatherViewModel, geocoder)
            CompleteScreen(weatherViewModel)
        }
    }



}

@Composable
fun CompleteScreen(weatherViewModel: WeatherViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherReportCard(weatherViewModel)
        ForecastWeatherCard(weatherViewModel)
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    /*WeatherTheme {
        //Greeting("Android")
    }*/

}