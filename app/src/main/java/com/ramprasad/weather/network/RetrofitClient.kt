package com.ramprasad.weatherapp.network

import com.ramprasad.weather.data.forecast.ForecastResponse
import com.ramprasad.weather.data.weather.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ramprasad on 6/11/23.
 */
interface RetrofitClient {

    @GET(LOCATION_BASED_WEATHER)
    suspend fun getWeatherBasedOnLocation(
        @Query("q") q: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherResponse>

    @GET(FORECAST_WEATHER)
    suspend fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<ForecastResponse>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "23096676a202def122e115d3c1120241"

        private const val LOCATION_BASED_WEATHER =
            "weather"

        private const val FORECAST_WEATHER =
            "forecast"
    }
}