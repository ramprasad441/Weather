package com.ramprasad.weather.commons

import com.ramprasad.weather.data.weather.CurrentWeatherResponse

/**
 * Created by Ramprasad on 6/11/23.
 */
sealed class StateOfResponse {
    class ERROR(val error: Throwable): StateOfResponse()
    object LOADING: StateOfResponse()
    class SUCCESS(val weatherReport: Any): StateOfResponse()

}