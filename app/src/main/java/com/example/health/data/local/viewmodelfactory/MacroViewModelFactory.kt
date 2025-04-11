package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.MacroRepository
import com.example.health.data.local.viewmodel.MacroViewModel


class MacroViewModelFactory(
    private val repository: MacroRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MacroViewModel::class.java)) {
            return MacroViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}