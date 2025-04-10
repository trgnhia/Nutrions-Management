package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.CustomFood
import com.example.health.data.local.repostories.CustomFoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CustomFoodViewModel(
    private val repository: CustomFoodRepository
) : ViewModel() {

    fun getAllByUser(uid: String): Flow<List<CustomFood>> =
        repository.getAllByUser(uid)

    fun searchByName(uid: String, query: String): Flow<List<CustomFood>> =
        repository.searchByName(uid, query)

    fun insert(food: CustomFood) = viewModelScope.launch {
        repository.insert(food)
    }

    fun update(food: CustomFood) = viewModelScope.launch {
        repository.update(food)
    }

    fun delete(food: CustomFood) = viewModelScope.launch {
        repository.delete(food)
    }
}
