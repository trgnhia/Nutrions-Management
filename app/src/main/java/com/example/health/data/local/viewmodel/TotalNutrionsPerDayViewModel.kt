package com.example.health.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.TotalNutrionsPerDay
import com.example.health.data.local.repostories.TotalNutrionsPerDayRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*

class TotalNutrionsPerDayViewModel(
    private val repository: TotalNutrionsPerDayRepository
) : ViewModel() {

    // ✅ Hàm lấy theo UID và ngày cụ thể (Flow)
    fun getByDateAndUid(date: Date, uid: String): StateFlow<TotalNutrionsPerDay?> =
        repository.getByDateAndUid(date, uid)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    // ✅ Lấy toàn bộ bản ghi theo UID
    fun getAllByUser(uid: String) =
        repository.getAllByUser(uid)

    // ✅ Thêm bản ghi
    fun insert(total: TotalNutrionsPerDay) = viewModelScope.launch {
        repository.insert(total)
    }

    // ✅ Cập nhật bản ghi
    fun update(total: TotalNutrionsPerDay) = viewModelScope.launch {
        repository.update(total)
    }

    // ✅ Xoá bản ghi
    fun delete(total: TotalNutrionsPerDay) = viewModelScope.launch {
        repository.delete(total)
    }

    // ✅ Tự động tạo hoặc cập nhật log tổng dinh dưỡng từ eaten_meal
    fun generateLog(uid: String, date: Date, dietType: Int) = viewModelScope.launch {
        repository.generateLog(uid, date, dietType)
    }
    // ✅ Kiểm tra tồn tại bản ghi theo ngày (dùng trong AutoInit hoặc Home)
    suspend fun getByDateAndUidOnce(date: Date, uid: String): TotalNutrionsPerDay? {
        return repository.getByDateAndUidOnce(date, uid)
    }

}
