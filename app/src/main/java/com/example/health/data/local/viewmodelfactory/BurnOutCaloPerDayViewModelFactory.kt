package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.BurnOutCaloPerDayRepository
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel

class BurnOutCaloPerDayViewModelFactory(
    private val repository: BurnOutCaloPerDayRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BurnOutCaloPerDayViewModel::class.java)) {
            return BurnOutCaloPerDayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
