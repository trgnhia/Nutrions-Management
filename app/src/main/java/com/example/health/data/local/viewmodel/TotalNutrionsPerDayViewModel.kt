package com.example.health.data.local.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.TotalNutrionsPerDay
import com.example.health.data.local.repostories.TotalNutrionsPerDayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class TotalNutrionsPerDayViewModel(
    private val repository: TotalNutrionsPerDayRepository,
    uid: String
) : ViewModel() {

    val allNutritionDays: StateFlow<List<TotalNutrionsPerDay>> =
        repository.getAllByUser(uid)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun getByDate(date: Date, uid: String): Flow<TotalNutrionsPerDay?> =
        repository.getByDateAndUid(date, uid)

    fun insert(total: TotalNutrionsPerDay) = viewModelScope.launch {
        repository.insert(total)
    }

    fun update(total: TotalNutrionsPerDay) = viewModelScope.launch {
        repository.update(total)
    }

    fun delete(total: TotalNutrionsPerDay) = viewModelScope.launch {
        repository.delete(total)
    }
}
