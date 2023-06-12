package com.ramprasad.weather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ramprasad.weather.R
import com.ramprasad.weather.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by Ramprasad on 6/12/23.
 */
@Composable
fun ForecastWeatherCard(weatherViewModel: WeatherViewModel) {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val weatherForecastState = weatherViewModel.forecastWeather.observeAsState().value
    val forecastList = weatherForecastState?.forecastList ?: emptyList()

    val weatherForecastLoadingState = weatherViewModel.forecastLoading.observeAsState().value

    if (weatherForecastLoadingState == true) {
        Text(text = "Loading Forecast Response")
    }

    /*Text(
        text = "Weekly Forecast Weather",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )*/
    LazyRow {
        items(forecastList.size) { position ->

            forecastList[position].let {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .wrapContentWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val date = it.dtTxt?.let { format.parse(it) }
                        //day
                        val formatDate = SimpleDateFormat("EEEE", Locale.getDefault())
                        // Format time
                        val formatTime = SimpleDateFormat("HH:mm a", Locale.getDefault())
                        val timeInHours = date?.let { formatTime.format(it) }
                        Text(
                            text = "${date?.let { formatDate.format(it) }}",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )

                        Text(
                            text = timeInHours.toString(),
                            color = Color.Blue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Image(
                            painter = rememberAsyncImagePainter(model = "https://openweathermap.org/img/wn/" + it.weather[0].icon + ".png"),
                            contentDescription = stringResource(R.string.content_description_background),
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )


                        Text(
                            text = it.main?.temp?.toString().orEmpty().plus(0x00B0.toChar()),
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
        }
    }
}