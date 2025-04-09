package com.example.health.data.local.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.TotalNutrionsPerDayRepository
import com.example.health.data.local.viewmodel.TotalNutrionsPerDayViewModel

class TotalNutrionsPerDayViewModelFactory(
    private val repository: TotalNutrionsPerDayRepository,

) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TotalNutrionsPerDayViewModel::class.java)) {
            return TotalNutrionsPerDayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
