package com.sunnyweather.android.logic.model

/**
 * 作者：created by zsh19 on 2024/12/30 09:30
 * 邮箱：zsh1980794141@126.com
 */
data class Weather(val realtime: RealTimeResponse.Realtime, val daily: DailyResponse.Daily)
