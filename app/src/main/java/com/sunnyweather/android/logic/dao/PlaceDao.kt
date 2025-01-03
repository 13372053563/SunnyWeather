package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse

/**
 * 作者：created by zsh19 on 2024/12/30 14:16
 * 邮箱：zsh1980794141@126.com
 */
object PlaceDao {
    fun savePlace(place: PlaceResponse.Place) {
        sharedPreferences().edit {
            putString("place", place.toString())
        }
    }

    fun getSavedPlace(): PlaceResponse.Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, PlaceResponse.Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}