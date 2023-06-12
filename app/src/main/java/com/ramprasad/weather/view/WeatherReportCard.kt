package com.ramprasad.weather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import com.ramprasad.weather.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ramprasad.weather.viewModel.WeatherViewModel

/**
 * Created by Ramprasad on 6/12/23.
 */
@Composable
fun WeatherReportCard(weatherViewModel: WeatherViewModel) {
    val weatherResponseState = weatherViewModel.weatherResponse.observeAsState().value
    val weatherLoadingState = weatherViewModel.weatherResponseLoading.observeAsState().value

    if (weatherLoadingState == true) {
        Text(text = "Loading Weather Response")
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            weatherResponseState?.apply {
                Text(
                    text = name.toString(),
                    color = Color.Blue,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = main?.temp.toString().plus(" \u2103"),
                    color = Color.Blue,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = rememberAsyncImagePainter(model = "https://openweathermap.org/img/wn/" + weather[0].icon + ".png"),
                    contentDescription = "Background",
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    contentScale = ContentScale.FillWidth,
                )

                weather.first().description?.let {
                    Text(
                        text = it,
                        color = Color.Blue,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    main?.tempMin?.let {
                        WeatherDetailDisplay(
                            value = it.toInt(),
                            unit = "hpa",
                            icon = ImageVector.vectorResource(id = R.drawable.presure),
                            iconTint = Color.Blue,
                            textStyle = TextStyle(color = Color.Blue)
                        )
                    }

                    main?.humidity?.let {
                        WeatherDetailDisplay(
                            value = it,
                            unit = "%",
                            icon = ImageVector.vectorResource(id = R.drawable.drop),
                            iconTint = Color.Blue,
                            textStyle = TextStyle(color = Color.Blue)
                        )
                    }
                    /*main?.let {
                        WeatherDataDisplay(
                            value = it.tempMin?.toInt()!! +  it.tempMax?.toInt()!!,
                            unit = "(Min/Max)",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                            iconTint = Color.Blue,
                            textStyle = TextStyle(color = Color.Blue)
                        )
                    }*/
                    wind?.speed?.toInt()?.let {
                        WeatherDetailDisplay(
                            value = it,
                            unit = "mph",
                            icon = ImageVector.vectorResource(id = R.drawable.wind),
                            iconTint = Color.Blue,
                            textStyle = TextStyle(color = Color.Blue)
                        )
                    }

                }
            }
        }

    }
}