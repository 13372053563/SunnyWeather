package com.sunnyweather.android.ui.place

import com.google.gson.annotations.SerializedName

/**
 * 作者：created by zsh19 on 2024/12/26 19:49
 * 邮箱：zsh1980794141@126.com
 */
data class PlaceResponse(val status: String, val places: List<Place>, val query:String)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)