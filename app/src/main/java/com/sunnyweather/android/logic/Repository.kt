package com.sunnyweather.android.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import com.sunnyweather.android.ui.place.Place
import kotlinx.coroutines.Dispatchers

/**
 * 作者：created by zsh19 on 2024/12/26 20:01
 * 邮箱：zsh1980794141@126.com
 */
object Repository {
    // liveData()函数是lifecycle-livedata-ktx库提供的一个非常强大且好用的功能，
    // 它可以自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，
    // 这样我们就可以在liveData()函数的代码块中调用任意的挂起函数了
    // Dispatchers.IO----子线程
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        // emit()方法将包装的结果发射出去，这个emit()方法其实类似于调用LiveData的setValue()方法来通知数据变化
        emit(result)
    }
}