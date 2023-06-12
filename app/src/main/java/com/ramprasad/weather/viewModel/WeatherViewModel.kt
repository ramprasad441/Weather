package com.ramprasad.weather.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramprasad.weather.commons.StateOfResponse
import com.ramprasad.weather.repository.WeatherDataRepository
import com.ramprasad.weather.data.forecast.ForecastResponse
import com.ramprasad.weather.data.weather.CurrentWeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ramprasad on 6/11/23.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _weatherResponse: MutableLiveData<CurrentWeatherResponse> =
        MutableLiveData()
    val weatherResponse: LiveData<CurrentWeatherResponse> get() = _weatherResponse

    private val _weatherResponseLoading: MutableLiveData<Boolean> = MutableLiveData()
    val weatherResponseLoading: LiveData<Boolean> get() = _weatherResponseLoading

    private val _weatherError: MutableLiveData<Throwable> = MutableLiveData()
    val weatherError: LiveData<Throwable> get() = _weatherError

    private val _forecast: MutableLiveData<ForecastResponse> = MutableLiveData()
    val forecastWeather: LiveData<ForecastResponse> get() = _forecast

    private val _forecastLoading: MutableLiveData<Boolean> = MutableLiveData()
    val forecastLoading: LiveData<Boolean> get() = _forecastLoading

    private val _forecastError: MutableLiveData<Throwable> = MutableLiveData()
    val forecastError: LiveData<Throwable> get() = _forecastError


    fun getWeatherReport(locality: String) {
        viewModelSafeScope.launch(ioDispatcher) {
            weatherDataRepository.getWeatherBasedOnLocation(locality).collect {
                when(it) {
                    is StateOfResponse.LOADING -> {
                        _weatherResponseLoading.postValue(true)
                    }
                    is StateOfResponse.SUCCESS -> {
                        _weatherResponse.postValue(it.weatherReport as CurrentWeatherResponse)
                        _weatherResponseLoading.postValue(false)
                    }
                    is StateOfResponse.ERROR -> {
                        _weatherError.postValue(it.error)
                        _weatherResponseLoading.postValue(false)
                    }
                }

            }
        }
    }

    fun getForecastWeather(lat: Double, long: Double) {
        viewModelSafeScope.launch(ioDispatcher) {
            weatherDataRepository.getForecastWeather(lat, long).collect {
                when(it) {
                    is StateOfResponse.LOADING -> {
                        _forecastLoading.postValue(true)
                    }
                    is StateOfResponse.SUCCESS -> {
                        _forecast.postValue(it.weatherReport as ForecastResponse)
                        _forecastLoading.postValue(false)
                    }
                    is StateOfResponse.ERROR -> {
                        _forecastError.postValue(it.error)
                        _forecastLoading.postValue(false)
                    }
                }
            }
        }
    }
}