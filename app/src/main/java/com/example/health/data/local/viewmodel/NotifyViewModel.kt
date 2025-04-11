package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.Notify
import com.example.health.data.local.repostories.NotifyRepository
import com.example.health.data.utils.DateUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import network.chaintech.kmp_date_time_picker.utils.now
import java.sql.Date
import java.time.LocalTime
import java.time.ZoneId

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
    }
}
