package com.example.health.data.local.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.BaseInfoRepository
import com.example.health.data.local.viewmodel.BaseInfoViewModel

class BaseInfoViewModelFactory(
    private val repository: BaseInfoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaseInfoViewModel::class.java)) {
            return BaseInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
