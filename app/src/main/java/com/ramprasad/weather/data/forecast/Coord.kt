package com.ramprasad.weather.data.forecast

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 6/11/23.
 */
data class Coord(
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null
)
