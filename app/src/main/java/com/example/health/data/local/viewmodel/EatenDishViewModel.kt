package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.EatenDish
import com.example.health.data.local.repostories.EatenDishRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class EatenDishViewModel(
    private val repository: EatenDishRepository
) : ViewModel() {

    fun getByDateAndType(date: Date, type: Int): Flow<List<EatenDish>> =
        repository.getByDateAndType(date, type)

    fun getByDate(date: Date): Flow<List<EatenDish>> =
        repository.getByDate(date)

    fun insert(dish: EatenDish,uid: String) = viewModelScope.launch {
        repository.insert(dish,uid)
    }

    fun update(dish: EatenDish,uid: String) = viewModelScope.launch {
        repository.update(dish,uid)
    }

    fun delete(dish: EatenDish,uid: String) = viewModelScope.launch {
        repository.delete(dish,uid)
    }
    // ✅ Fetch toàn bộ dữ liệu từ Firestore về Room
    fun fetchFromRemote(uid: String) = viewModelScope.launch {
        repository.fetchFromRemote(uid)
    }

    // ✅ Sync nếu Room đang rỗng (giả sử bạn thêm hàm kiểm tra count trong Repository)
    fun syncIfNeeded(uid: String) = viewModelScope.launch {
        // TODO: nếu cần chính xác hơn, bạn nên thêm hàm getCountByUid() trong repository
        repository.fetchFromRemote(uid)
    }
}
