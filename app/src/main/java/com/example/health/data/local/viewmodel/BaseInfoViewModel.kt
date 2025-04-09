package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.BaseInfo
import com.example.health.data.local.repostories.BaseInfoRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class BaseInfoViewModel(
    private val repository: BaseInfoRepository
) : ViewModel() {

    val baseInfo: StateFlow<BaseInfo?> = repository.getBaseInfo()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun insertBaseInfo(baseInfo: BaseInfo) = viewModelScope.launch {
        repository.insertBaseInfo(baseInfo)
    }

    fun deleteBaseInfo() = viewModelScope.launch {
        repository.deleteBaseInfo()
    }

    fun updateBaseInfo(baseInfo: BaseInfo) = viewModelScope.launch {
        repository.updateBaseInfo(baseInfo)
    }

    fun fetchFromRemote(uid: String) = viewModelScope.launch {
        repository.fetchFromRemote(uid)
    }
    // ✅ Thêm hàm này để kiểm tra rồi mới fetch
    fun syncIfNeeded(uid: String) = viewModelScope.launch {
        if (baseInfo.value == null) {
            fetchFromRemote(uid)
        }
    }
}
