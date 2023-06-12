package com.ramprasad.weather.data.forecast

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 6/11/23.
 */
data class Clouds(@SerializedName("all") var all: Int? = null)
