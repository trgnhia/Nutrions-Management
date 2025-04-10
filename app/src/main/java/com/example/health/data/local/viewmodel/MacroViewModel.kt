package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.Macro
import com.example.health.data.local.repostories.MacroRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MacroViewModel(
    private val repository: MacroRepository
) : ViewModel() {

    val macro: StateFlow<Macro?> = repository.getMacro()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun insert(macro: Macro) = viewModelScope.launch {
        repository.insert(macro)
    }

    fun update(macro: Macro) = viewModelScope.launch {
        repository.update(macro)
    }

    fun fetchFromRemote(uid: String) = viewModelScope.launch {
        repository.fetchFromRemote(uid)
    }

    fun syncIfNeeded(uid: String) = viewModelScope.launch {
        if (macro.value == null) {
            fetchFromRemote(uid)
        }
    }
}