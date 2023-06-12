package com.ramprasad.weather.data.forecast

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 6/11/23.
 */
data class Weather(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null
)
