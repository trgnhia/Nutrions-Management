package com.example.health.data.local.viewmodelfactory.quyen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.quyen.TotalNutrionsPerDayRepository
import com.example.health.data.local.viewmodel.quyen.TotalNutrionsPerDayViewModel

class TotalNutrionsPerDayViewModelFactory(
    private val repository: TotalNutrionsPerDayRepository,
    private val uid: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TotalNutrionsPerDayViewModel::class.java)) {
            return TotalNutrionsPerDayViewModel(repository, uid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
