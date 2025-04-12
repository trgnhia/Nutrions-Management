package com.example.health.data.local.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.ExerciseLog
import com.example.health.data.local.repostories.ExerciseLogRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class ExerciseLogViewModel(
    private val repository: ExerciseLogRepository
) : ViewModel() {

    fun getAllByUser(uid: String): Flow<List<ExerciseLog>> =
        repository.getAllByUser(uid)

    fun getByDate(uid: String, date: Date): Flow<List<ExerciseLog>> =
        repository.getByDate(uid, date)

    suspend fun getTotalCalo(uid: String, date: Date): Int =
        repository.getTotalCaloBurnedByDate(uid, date)

    fun insert(log: ExerciseLog) = viewModelScope.launch {
        repository.insert(log)
    }

    fun update(log: ExerciseLog) = viewModelScope.launch {
        repository.update(log)
    }

    fun delete(log: ExerciseLog) = viewModelScope.launch {
        repository.delete(log)
    }
    // ✅ Đồng bộ toàn bộ log từ Firestore về Room
    fun fetchFromRemote(uid: String) = viewModelScope.launch {
        repository.fetchFromRemote(uid)
    }

    // ✅ Chỉ fetch nếu cần (Room đang trống)
    fun syncIfNeeded(uid: String) = viewModelScope.launch {
        // Có thể thêm kiểm tra repository.getCount(uid) nếu muốn tối ưu
        repository.fetchFromRemote(uid)
    }
}
