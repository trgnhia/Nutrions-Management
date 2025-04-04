package com.example.health.data.local.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.AccountRepository
import com.example.health.data.remote.auth.AuthViewModel

class AuthViewModelFactory(
    private val application: Application,
    private val accountRepository: AccountRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(application, accountRepository) as T
    }
}