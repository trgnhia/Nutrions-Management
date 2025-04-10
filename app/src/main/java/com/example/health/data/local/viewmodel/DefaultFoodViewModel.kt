package com.example.health.data.local.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.DefaultFood
import com.example.health.data.local.repostories.DefaultFoodRepository
import com.example.health.data.utils.downloadImageAndSave
import com.example.health.data.utils.toSafeFileName
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DefaultFoodViewModel(
    private val repository: DefaultFoodRepository
) : ViewModel() {

    val defaultFoods: StateFlow<List<DefaultFood>> = repository.getAll()
        .distinctUntilChanged() // ✅ Thêm dòng này để tránh emit giá trị giống nhau liên tục
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // ✅ Tải từng món ăn, xử lý ảnh và insert ngay
    fun loadDefaultFoods(context: Context) = viewModelScope.launch {
        repository.fetchRemoteAndInsertEach { food ->
            val safeFileName = "${food.Name.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, food.UrlImage, safeFileName)
            val localPath = file?.absolutePath ?: food.UrlImage
            val updated = food.copy(UrlImage = localPath)

            repository.insert(updated)
        }
    }

    fun syncIfNeeded(context: Context) = viewModelScope.launch {
        if (defaultFoods.value.isEmpty()) {
            Log.d("DefaultFoodViewModel", "Room empty → loading from Firestore...")
            loadDefaultFoods(context)
        } else {
            Log.d("DefaultFoodViewModel", "Room already has data → skip loading.")
        }
    }

    fun getByType(type: Int): StateFlow<List<DefaultFood>> =
        repository.getByType(type)
            .distinctUntilChanged() // 🔥 bắt buộc có!
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    // ✅ Lấy món ăn ngẫu nhiên theo loại
    fun getRandomFoodsByType(count: Int, type: Int): StateFlow<List<DefaultFood>> {
        return flow {
            val foods = repository.getRandomFoodsByType(count, type)
            emit(foods)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }
}
