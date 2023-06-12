package com.ramprasad.weather.data.forecast

import com.google.gson.annotations.SerializedName
import com.ramprasad.weather.data.forecast.City
import com.ramprasad.weather.data.forecast.ForecastList

/**
 * Created by Ramprasad on 6/11/23.
 */
data class ForecastResponse(
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var forecastList: ArrayList<ForecastList> = arrayListOf(),
    @SerializedName("city") var city: City? = City()
)


