package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.EatenMeal
import com.example.health.data.local.repostories.EatenMealRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class EatenMealViewModel(
    private val repository: EatenMealRepository
) : ViewModel() {

    val allMeals = repository.getAllMeals()

    fun getMealsByDate(date: Date) = repository.getMealsByDate(date)

    fun insert(meal: EatenMeal) = viewModelScope.launch {
        repository.insert(meal)
    }

    fun update(meal: EatenMeal) = viewModelScope.launch {
        repository.update(meal)
    }

    fun delete(meal: EatenMeal) = viewModelScope.launch {
        repository.delete(meal)
    }

    suspend fun getMealByDateAndType(date: Date, type: Int): EatenMeal? {
        return repository.getMealByDateAndType(date, type)
    }
}
