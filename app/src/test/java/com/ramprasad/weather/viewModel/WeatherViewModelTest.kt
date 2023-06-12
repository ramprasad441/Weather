package com.ramprasad.weather.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ramprasad.weather.commons.StateOfResponse
import com.ramprasad.weather.repository.WeatherDataRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import com.google.common.truth.Truth.assertThat
import com.ramprasad.weather.data.forecast.ForecastResponse
import com.ramprasad.weather.data.weather.CurrentWeatherResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ramprasad on 6/12/23.
 */
@ExperimentalCoroutinesApi
class WeatherViewModelTest {


    private lateinit var targetTest: WeatherViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockRepository = mockk<WeatherDataRepository>(relaxed = true)


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        targetTest = WeatherViewModel(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testWeatherReportResponseError() = runBlocking {
        every { mockRepository.getWeatherBasedOnLocation("NewYork") } returns flowOf(
            StateOfResponse.ERROR(Throwable("Error"))
        )
        val loadingStateList = mutableListOf<Boolean>()
        targetTest.weatherResponseLoading.observeForever {
            loadingStateList.add(it)
        }
        val errorStateList = mutableListOf<Throwable>()
        targetTest.weatherError.observeForever {
            errorStateList.add(it)
        }

        targetTest.getWeatherReport("NewYork")

        assertThat(loadingStateList).hasSize(1)
        assertThat(loadingStateList.first()).isEqualTo(false)
        assertThat(errorStateList[0]).isInstanceOf(Throwable::class.java)
        assertThat(errorStateList.first().localizedMessage).isEqualTo("Error")
    }

    @Test
    fun testWeatherReportResponseLoading() = runBlocking {

        every { mockRepository.getWeatherBasedOnLocation("NewYork") } returns flowOf(
            StateOfResponse.LOADING
        )
        val stateList = mutableListOf<Any>()
        targetTest.weatherResponseLoading.observeForever {
            stateList.add(it)
        }

        targetTest.getWeatherReport("NewYork")

        assertThat(stateList).hasSize(1)
        assertThat(stateList.first()).isEqualTo(true)
    }


    @Test
    fun testWeatherReportSuccess() = runBlocking {
        every { mockRepository.getWeatherBasedOnLocation("NewYork") } returns flowOf(
            StateOfResponse.SUCCESS(CurrentWeatherResponse())
        )
        val loadingStateList = mutableListOf<Any>()
        targetTest.weatherResponseLoading.observeForever {
            loadingStateList.add(it)
        }
        val stateList = mutableListOf<Any>()
        targetTest.weatherResponse.observeForever {
            stateList.add(it)
        }
        targetTest.getWeatherReport("NewYork")

        assertThat(loadingStateList).hasSize(1)
        assertThat(loadingStateList[0]).isEqualTo(false)
        assertThat(stateList).hasSize(1)
        assertThat(stateList[0]).isInstanceOf(CurrentWeatherResponse::class.java)
    }

    @Test
    fun testForecastWeatherResponseError() = runBlocking {
        every { mockRepository.getForecastWeather(47.6062083, -122.33207) } returns flowOf(
            StateOfResponse.ERROR(Throwable("Error"))
        )
        val loadingStateList = mutableListOf<Boolean>()
        targetTest.forecastLoading.observeForever {
            loadingStateList.add(it)
        }
        val errorStateList = mutableListOf<Throwable>()
        targetTest.forecastError.observeForever {
            errorStateList.add(it)
        }

        targetTest.getForecastWeather(47.6062083, -122.33207)

        assertThat(loadingStateList).hasSize(1)
        assertThat(loadingStateList.first()).isEqualTo(false)
        assertThat(errorStateList[0]).isInstanceOf(Throwable::class.java)
        assertThat(errorStateList.first().localizedMessage).isEqualTo("Error")
    }

    @Test
    fun testForecastWeatherResponseLoading() = runBlocking {
        every { mockRepository.getForecastWeather(47.6062083, -122.33207) } returns flowOf(
            StateOfResponse.LOADING
        )
        val stateList = mutableListOf<Any>()
        targetTest.forecastLoading.observeForever {
            stateList.add(it)
        }

        targetTest.getForecastWeather(47.6062083, -122.33207)

        assertThat(stateList).hasSize(1)
        assertThat(stateList.first()).isEqualTo(true)
    }

    @Test
    fun testForecastWeatherResponseSuccess() = runBlocking {
        every { mockRepository.getForecastWeather(47.6062083, -122.33207) } returns flowOf(
            StateOfResponse.SUCCESS(ForecastResponse())
        )
        val loadingStateList = mutableListOf<Any>()
        targetTest.forecastLoading.observeForever {
            loadingStateList.add(it)
        }
        val stateList = mutableListOf<Any>()
        targetTest.forecastWeather.observeForever {
            stateList.add(it)
        }
        targetTest.getForecastWeather(47.6062083, -122.33207)

        assertThat(loadingStateList).hasSize(1)
        assertThat(loadingStateList[0]).isEqualTo(false)
        assertThat(stateList).hasSize(1)
        assertThat(stateList[0]).isInstanceOf(ForecastResponse::class.java)
    }

}