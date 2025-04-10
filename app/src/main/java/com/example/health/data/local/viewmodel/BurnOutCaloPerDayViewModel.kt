package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.local.repostories.BurnOutCaloPerDayRepository
import kotlinx.coroutines.launch
import java.util.*
class BurnOutCaloPerDayViewModel(
    private val repository: BurnOutCaloPerDayRepository
) : ViewModel() {

    fun insert(data: BurnOutCaloPerDay) = viewModelScope.launch {
        repository.insert(data)
    }

    fun update(data: BurnOutCaloPerDay) = viewModelScope.launch {
        repository.update(data)
    }

    fun delete(data: BurnOutCaloPerDay) = viewModelScope.launch {
        repository.delete(data)
    }

    suspend fun getByDate(date: Date) = repository.getByDate(date)

    suspend fun getTotalCaloBurnedByDate(uid: String, date: Date) =
        repository.getTotalCaloBurnedByDate(uid, date)

    // ✅ Hàm gọi từ UI để sinh log BurnOut mới cho ngày cụ thể
    fun generateLog(uid: String, date: Date) = viewModelScope.launch {
        repository.generateBurnOutLog(uid, date)
    }
}
