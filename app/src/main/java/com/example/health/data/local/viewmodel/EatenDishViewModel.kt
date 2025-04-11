package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.local.repostories.EatenDishRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class EatenDishViewModel(
    private val repository: EatenDishRepository
) : ViewModel() {

    fun getByDateAndType(date: Date, type: Int): Flow<List<EatenDish>> =
        repository.getByDateAndType(date, type)

    fun getByDate(date: Date): Flow<List<EatenDish>> =
        repository.getByDate(date)

    fun insert(dish: EatenDish) = viewModelScope.launch {
        repository.insert(dish)
    }

    fun update(dish: EatenDish) = viewModelScope.launch {
        repository.update(dish)
    }

    fun delete(dish: EatenDish) = viewModelScope.launch {
        repository.delete(dish)
    }
}
