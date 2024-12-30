package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 作者：created by zsh19 on 2024/12/26 19:45
 * 邮箱：zsh1980794141@126.com
 */
class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "UTZimM83dOI01xll"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}