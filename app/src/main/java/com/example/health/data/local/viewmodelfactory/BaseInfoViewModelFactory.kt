package com.example.health.data.local.viewmodelfactory


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health.data.local.repostories.BaseInfoRepository
import com.example.health.data.local.viewmodel.BaseInfoViewModel

class BaseInfoViewModelFactory(
    private val application: Application,
    private val repository: BaseInfoRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BaseInfoViewModel(application, repository) as T
    }
}
