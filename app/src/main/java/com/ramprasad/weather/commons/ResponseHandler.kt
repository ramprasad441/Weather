package com.ramprasad.weather.commons

/**
 * Created by Ramprasad on 6/11/23.
 */
class EmptyResponseMessage(message: String = "The Response is null/empty") : Exception(message)
class FailureResponse(message: String = "Error: Failed to fetch the countries list response") : Exception(message)