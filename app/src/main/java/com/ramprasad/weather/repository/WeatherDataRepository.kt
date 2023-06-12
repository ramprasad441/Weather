package com.ramprasad.weather.repository


import com.ramprasad.weather.commons.EmptyResponseMessage
import com.ramprasad.weather.commons.FailureResponse
import com.ramprasad.weather.commons.StateOfResponse
import com.ramprasad.weatherapp.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ramprasad on 6/11/23.
 */


class WeatherDataRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient
) : WeatherDataRepository {

    override fun getWeatherBasedOnLocation(locality: String): Flow<StateOfResponse> =
        flow {
            emit(StateOfResponse.LOADING)
            try {
                val response = retrofitClient.getWeatherBasedOnLocation(locality)

                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(StateOfResponse.SUCCESS(it))
                    } ?: throw EmptyResponseMessage()
                } else {
                    throw FailureResponse()
                }
            } catch (exception: Exception) {
                emit(StateOfResponse.ERROR(exception))
            }
        }

    override fun getForecastWeather(lat: Double, long: Double): Flow<StateOfResponse> =
        flow {
            emit(StateOfResponse.LOADING)
            try {
                val response = retrofitClient.getForecastWeather(lat, long)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(StateOfResponse.SUCCESS(it))
                    } ?: throw EmptyResponseMessage()

                } else {
                    throw FailureResponse()
                }
            } catch (exception: Exception) {
                emit(StateOfResponse.ERROR(exception))
            }

        }

}

interface WeatherDataRepository {
    fun getWeatherBasedOnLocation(locality: String): Flow<StateOfResponse>
    fun getForecastWeather(lat: Double, long: Double): Flow<StateOfResponse>
}