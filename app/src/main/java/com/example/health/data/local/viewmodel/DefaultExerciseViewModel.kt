package com.example.health.data.local.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.DefaultExercise
import com.example.health.data.local.repostories.DefaultExerciseRepository
import com.example.health.data.utils.downloadImageAndSave
import com.example.health.data.utils.toSafeFileName
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultExerciseViewModel(
    private val repository: DefaultExerciseRepository
) : ViewModel() {

    val defaultExercises: StateFlow<List<DefaultExercise>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun getById(id: String, onResult: (DefaultExercise?) -> Unit) = viewModelScope.launch {
        onResult(repository.getById(id))
    }

    // ✅ Tải từng item → xử lý ảnh → insert vào Room ngay
    fun loadDefaultExercises(context: Context) = viewModelScope.launch {
        repository.fetchRemoteAndInsertEach { exercise ->
            val safeFileName = "${exercise.Name.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, exercise.UrlImage, safeFileName)
            val localPath = file?.absolutePath ?: exercise.UrlImage
            val updated = exercise.copy(UrlImage = localPath)

            repository.insert(updated)
        }
    }

    // ✅ Kiểm tra Room trước khi tải
    fun syncIfNeeded(context: Context) = viewModelScope.launch {
        if (defaultExercises.value.isEmpty()) {
            Log.d("DefaultExerciseViewModel", "Room empty → loading from Firestore...")
            loadDefaultExercises(context)
        } else {
            Log.d("DefaultExerciseViewModel", "Room already has data → skip loading.")
        }
    }
}
