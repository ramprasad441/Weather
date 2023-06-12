package com.ramprasad.weather.data.weather

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 6/11/23.
 */
data class Sys(
    @SerializedName("type") var type: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null
)
