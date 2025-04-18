package com.example.health.data.local.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.alarm.AlarmScheduler
import com.example.health.data.local.entities.Notify
import com.example.health.data.local.repostories.NotifyRepository
import com.example.health.data.utils.DateUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.sql.Date

class NotifyViewModel(
    private val repository: NotifyRepository
) : ViewModel() {

    fun getAllByUid(uid: String): StateFlow<List<Notify>> {
        return repository.getAllByUid(uid).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }

    fun updateNotifyTime(uid: String, mealId: String, newTime: Date, context: Context) {
        viewModelScope.launch {
            val notify = Notify(
                id = mealId,
                Uid = uid,
                Message = "It's time for ${mealId.replaceFirstChar { it.uppercase() }}!",
                NotifyTime = newTime
            )
            repository.update(notify)
            AlarmScheduler.scheduleAlarm(context, notify)
        }
    }

    fun initDefaultNotifications(uid: String) {
        val meals = listOf("Breakfast", "Lunch", "Dinner", "Snack")
        val times = listOf(7, 12, 18, 21)

        val defaultNotifies = meals.mapIndexed { index, meal ->
            Notify(
                id = meal.lowercase(),
                Uid = uid,
                Message = "It's time for $meal!",
                NotifyTime = DateUtils.toTodayDate(times[index], 0)
            )
        }

        viewModelScope.launch {
            repository.insertAll(defaultNotifies)
        }
        // ✅ Fetch dữ liệu từ Firestore về Room
        fun fetchFromRemote(uid: String) = viewModelScope.launch {
            repository.fetchFromRemote(uid)
        }

        // ✅ Chỉ fetch nếu cần (ví dụ app vừa mở, Room trống)
        fun syncIfNeeded(uid: String) = viewModelScope.launch {
            // Có thể thêm logic kiểm tra count trong repository nếu cần
            repository.fetchFromRemote(uid)
        }
    }
}

