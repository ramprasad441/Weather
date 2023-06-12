package com.ramprasad.weather.repository

import com.ramprasad.weather.commons.StateOfResponse
import com.ramprasad.weatherapp.network.RetrofitClient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Ramprasad on 6/12/23.
 */
class WeatherDataRepositoryImplTest {

    private lateinit var mockRetrofitClient: RetrofitClient


    private lateinit var repo: WeatherDataRepositoryImpl

    @Before
    fun before() {
        mockRetrofitClient = Mockito.mock(RetrofitClient::class.java)
        repo = WeatherDataRepositoryImpl(mockRetrofitClient)

    }

    @Test
    fun testWeatherErrorData() = runTest {
        Mockito.`when`(mockRetrofitClient.getWeatherBasedOnLocation("NewYork")).thenAnswer {
            StateOfResponse.ERROR(Throwable())
        }
        val res: List<StateOfResponse> = repo.getWeatherBasedOnLocation("NewYork").toList()

        assert(true)
        assert((res[1] is StateOfResponse.ERROR))
    }


    @Test
    fun testForecastErrorData() = runTest {
        Mockito.`when`(mockRetrofitClient.getForecastWeather(47.6062083, -122.33207)).thenAnswer {
            StateOfResponse.ERROR(Throwable())
        }
        val res: List<StateOfResponse> = repo.getForecastWeather(47.6062083, -122.33207).toList()

        assert(true)
        assert((res[1] is StateOfResponse.ERROR))
    }


}