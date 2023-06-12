package com.ramprasad.weather.data.weather

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 6/11/23.
 */
data class Wind(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null
)
