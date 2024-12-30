package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * 作者：created by zsh19 on 2024/12/30 09:19
 * 邮箱：zsh1980794141@126.com
 */
data class RealTimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: Realtime)
    data class Realtime(
        val temperature: Float, val skycon: String,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)
    data class AQI(val chn: Float)
}