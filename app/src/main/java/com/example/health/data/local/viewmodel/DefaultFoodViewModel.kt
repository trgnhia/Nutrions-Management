package com.example.health.data.local.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.local.repostories.DefaultFoodRepository
import com.example.health.data.utils.downloadImageAndSave
import com.example.health.data.utils.toSafeFileName
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultFoodViewModel(
    private val repository: DefaultFoodRepository
) : ViewModel() {

    val defaultFoods: StateFlow<List<DefaultFood>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun loadDefaultFoods(context: Context) = viewModelScope.launch {
        val remoteFoods = repository.fetchRemoteDefaultFoods()

        val updatedFoods = remoteFoods.map { food ->
            val safeFileName = "${food.Name.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, food.UrlImage, safeFileName)
            val localPath = file?.absolutePath ?: food.UrlImage
            food.copy(UrlImage = localPath)
        }

        repository.insertAll(updatedFoods)
    }
}
