package com.example.rhythm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RhythmViewModel(private val repository: RhythmRepository) : ViewModel() {
    private val _state = MutableStateFlow(repository.load())
    val state: StateFlow<RhythmState> = _state.asStateFlow()

    fun togglePaused() = update { copy(paused = !paused).also { repository.setPaused(it.paused) } }
    fun setWaterEnabled(value: Boolean) = update { copy(waterEnabled = value).also { repository.setWaterEnabled(value) } }
    fun setPushupsEnabled(value: Boolean) = update { copy(pushupsEnabled = value).also { repository.setPushupsEnabled(value) } }
    fun setTarget(value: Int) { if (value > 0) update { copy(target = value).also { repository.setTarget(value) } } }
    fun setHours(start: Int, end: Int) {
        if (start in 0..23 && end in 0..23 && start != end) update {
            copy(startHour = start, endHour = end).also { repository.setHours(start, end) }
        }
    }
    fun completeWater() = update { copy(waterCount = waterCount + 1).also { repository.setWaterCount(it.waterCount) } }
    fun undoWater() = update {
        val newCount = (waterCount - 1).coerceAtLeast(0)
        copy(waterCount = newCount).also { repository.setWaterCount(it.waterCount) }
    }

    fun completePushups() = update { copy(pushupCount = pushupCount + target).also { repository.setPushupCount(it.pushupCount) } }
    fun addPushups(amount: Int) {
        if (amount > 0) update { copy(pushupCount = pushupCount + amount).also { repository.setPushupCount(it.pushupCount) } }
    }
    fun undoPushups(amount: Int = state.value.target) = update {
        val newCount = (pushupCount - amount).coerceAtLeast(0)
        copy(pushupCount = newCount).also { repository.setPushupCount(it.pushupCount) }
    }

    fun resetToday() = update {
        copy(waterCount = 0, pushupCount = 0).also {
            repository.setWaterCount(0)
            repository.setPushupCount(0)
        }
    }

    private inline fun update(transform: RhythmState.() -> RhythmState) {
        _state.value = _state.value.transform()
    }
}
