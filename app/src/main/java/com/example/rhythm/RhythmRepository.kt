package com.example.rhythm

import android.content.SharedPreferences

data class RhythmState(
    val paused: Boolean = false,
    val waterEnabled: Boolean = true,
    val pushupsEnabled: Boolean = true,
    val target: Int = 5,
    val startHour: Int = 9,
    val endHour: Int = 21,
    val waterCount: Int = 0,
    val pushupCount: Int = 0
)

class RhythmRepository(private val preferences: SharedPreferences) {
    fun load(): RhythmState = RhythmState(
        paused = preferences.getBoolean("paused", false),
        waterEnabled = preferences.getBoolean("water", true),
        pushupsEnabled = preferences.getBoolean("pushups", true),
        target = preferences.getInt("target", 5),
        startHour = preferences.getInt("startHour", 9),
        endHour = preferences.getInt("endHour", 21),
        waterCount = preferences.getInt("wc", 0),
        pushupCount = preferences.getInt("pc", 0)
    )

    fun setPaused(value: Boolean) = preferences.edit().putBoolean("paused", value).apply()
    fun setWaterEnabled(value: Boolean) = preferences.edit().putBoolean("water", value).apply()
    fun setPushupsEnabled(value: Boolean) = preferences.edit().putBoolean("pushups", value).apply()
    fun setTarget(value: Int) = preferences.edit().putInt("target", value).apply()
    fun setHours(start: Int, end: Int) = preferences.edit().putInt("startHour", start).putInt("endHour", end).apply()
    fun setWaterCount(value: Int) = preferences.edit().putInt("wc", value).apply()
    fun setPushupCount(value: Int) = preferences.edit().putInt("pc", value).apply()
}
