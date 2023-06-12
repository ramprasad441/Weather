package com.ramprasad.weather.data.forecast

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 6/11/23.
 */
data class ForecastList(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Float? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("dt_txt") var dtTxt: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }
        other as ForecastList
        if (dt != other.dt) {
            return false
        }
        if (main != other.main) {
            return false
        }
        if (weather != other.weather) {
            return false
        }
        if (clouds != other.clouds) {
            return false
        }
        if (wind != other.wind) {
            return false
        }
        if (visibility != other.visibility) {
            return false
        }
        if (pop != other.pop) {
            return false
        }
        if (sys != other.sys) {
            return false
        }
        if (dtTxt != other.dtTxt) {
            return false
        }
        return true
    }



    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

    fun getForecastList(): MutableList<ForecastList> {
        val forecastList = mutableListOf<ForecastList>()
        //forecastList.add()
        return forecastList

    }
}

