package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.Account
import com.example.health.data.local.repostories.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: AccountRepository
) : ViewModel() {

    val account: StateFlow<Account?> = repository.getAccount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun insertAccount(account: Account) = viewModelScope.launch {
        repository.insertAccount(account)
    }

    fun deleteAccount() = viewModelScope.launch {
        repository.deleteAccount()
    }

    fun updateStatus(uid: String, status: String) = viewModelScope.launch {
        repository.updateStatus(uid, status)
    }

    fun fetchFromRemote(uid: String) = viewModelScope.launch {
        repository.fetchFromRemote(uid)
    }
    // ✅ Thêm hàm này để kiểm tra rồi mới fetch
    fun syncIfNeeded(uid: String) = viewModelScope.launch {
        if (account.value == null) {
            fetchFromRemote(uid)
        }
    }
}
