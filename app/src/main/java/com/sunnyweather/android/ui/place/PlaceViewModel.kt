package com.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sunnyweather.android.logic.Repository

/**
 * 作者：created by zsh19 on 2024/12/26 20:09
 * 邮箱：zsh1980794141@126.com
 */
class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()

    val placeLiveDta = searchLiveData.switchMap { Repository.searchPlaces(it) }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}