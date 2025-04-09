package com.example.health.data.local.viewmodelfactory.quyen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.quyen.MacroRepository
import com.example.health.data.local.viewmodel.quyen.MacroViewModel


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