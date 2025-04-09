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

    fun loadDefaultExercises(context: Context) = viewModelScope.launch {
        Log.e("", "loadDefaultExercises: Loaddefault exercise ", )
        val remote = repository.fetchRemoteDefaultExercises()
        val updated = remote.map { ex ->
            val safeFileName = "${ex.Name.toSafeFileName()}.jpg"
            val file = downloadImageAndSave(context, ex.UrlImage, safeFileName)
            val localPath = file?.absolutePath ?: ex.UrlImage
            ex.copy(UrlImage = localPath)
        }

        repository.insertAll(updated)
    }

    fun getById(id: String, onResult: (DefaultExercise?) -> Unit) = viewModelScope.launch {
        onResult(repository.getById(id))
    }
}
