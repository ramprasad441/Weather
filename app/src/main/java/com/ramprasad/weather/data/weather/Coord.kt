package com.ramprasad.weather.data.weather

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 6/11/23.
 */
data class Coord(
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("lat") var lat: Double? = null
)
