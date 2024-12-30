package com.sunnyweather.android.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.PlaceResponse
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * 作者：created by zsh19 on 2024/12/26 20:01
 * 邮箱：zsh1980794141@126.com
 */
object Repository {

    fun savePlace(place: PlaceResponse.Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    /**
     * 在这里同时发起两个网络请求，由于两个请求没有先后顺序，所以需要同时等待两个网络的结果，这里就可以使用
     * async和await等待网络请求的结果。async需要在coroutineScope{}里面使用。
     */
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeResponse(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyResponse(lng, lat)
            }
            val realTimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realTimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realTimeResponse.result.realtime, dailyResponse.result.daily)
                Log.e("Repository", weather.toString())
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realTimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    /**
     * liveData()函数是lifecycle-livedata-ktx库提供的一个非常强大且好用的功能，
     * 它可以自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，
     * 这样我们就可以在liveData()函数的代码块中调用任意的挂起函数了
     * Dispatchers.IO----子线程
     */

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    /**
     * 定义统一的try catch处理方法。这是一个按照liveData()函数的参数接收标准定义的一个高阶函数。
     * 在fire()函数的内部会先调用一下liveData()函数，然后在liveData()函数的代码块中统一进行了try catch处理，
     * 并在try语句中调用传入的Lambda表达式中的代码，最终获取Lambda表达式的执行结果并调用emit()方法发射出去。
     * CoroutineContext：Dispatchers.IO
     * block：网络请求
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            // emit()方法将包装的结果发射出去，这个emit()方法其实类似于调用LiveData的setValue()方法来通知数据变化
            emit(result)
        }
}