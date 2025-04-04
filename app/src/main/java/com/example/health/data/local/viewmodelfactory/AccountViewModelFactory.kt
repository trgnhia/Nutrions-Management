package com.example.health.data.local.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.AccountRepository
import com.example.health.data.local.viewmodel.AccountViewModel

class AccountViewModelFactory(
    private val application: Application,
    private val repository: AccountRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountViewModel(application, repository) as T
    }
}
