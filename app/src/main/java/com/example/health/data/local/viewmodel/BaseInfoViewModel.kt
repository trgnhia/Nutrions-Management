package com.example.health.data.local.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.BaseInfo
import com.example.health.data.local.repostories.BaseInfoRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class BaseInfoViewModel(
    private val repository: BaseInfoRepository
) : ViewModel() {

    /**
     * 📦 baseInfo là StateFlow được lấy từ Room thông qua Repository
     * Dùng để quan sát trong UI bằng collectAsState()
     *
     * ⚠️ Lưu ý:
     * - Sử dụng `SharingStarted.WhileSubscribed()` → chỉ active khi UI đang collect
     * - Nếu chưa collect xong → baseInfo.value có thể = null
     */
    val baseInfo: StateFlow<BaseInfo?> = repository.getBaseInfo()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    /**
     * ✅ Hàm để thêm thông tin cơ bản mới vào database (Room + Firestore)
     * Thường gọi sau khi người dùng nhập xong các thông tin: tuổi, chiều cao, cân nặng...
     */
    fun insertBaseInfo(baseInfo: BaseInfo) = viewModelScope.launch {
        repository.insertBaseInfo(baseInfo)
    }

    /**
     * ❌ Hàm xoá dữ liệu base info trong Room (và đồng bộ xoá trên Firestore)
     * Cần dùng cẩn thận vì mất hết dữ liệu người dùng.
     */
    fun deleteBaseInfo() = viewModelScope.launch {
        repository.deleteBaseInfo()
    }

    /**
     * 🔄 Hàm cập nhật toàn bộ BaseInfo
     * Gọi khi người dùng thay đổi dữ liệu: cân nặng, chiều cao, độ tuổi...
     */
    fun updateBaseInfo(baseInfo: BaseInfo) = viewModelScope.launch {
        repository.updateBaseInfo(baseInfo)
    }

    /**
     * 🌐 Đồng bộ dữ liệu từ Firestore về Room theo uid
     * Dùng khi vừa đăng nhập xong hoặc phát hiện trong Room chưa có dữ liệu
     */
    fun fetchFromRemote(uid: String) = viewModelScope.launch {
        repository.fetchFromRemote(uid)
    }

    /**
     * 🛠 Gọi để kiểm tra nếu Room chưa có dữ liệu thì mới fetch từ Firestore
     * Tránh gọi fetch liên tục, chỉ dùng khi khởi động hoặc login
     */
    fun syncIfNeeded(uid: String) = viewModelScope.launch {
        if (baseInfo.value == null) {
            fetchFromRemote(uid)
        }
    }

    /**
     * 🚀 Bắt đầu chế độ ăn (start diet plan)
     * Cập nhật trường isDiet thành mã tương ứng (1 = Vegan, 2 = HighProtein, 3 = Keto, ...)
     *
     * ✅ Ưu điểm: dùng trực tiếp uid để đảm bảo không bị null
     * ⚠️ Tránh dùng baseInfo.value?.Uid vì nó có thể bị null nếu chưa collect xong
     */
    fun startDiet(uid: String, dietCode: Int) = viewModelScope.launch {
        repository.updateIsDiet(uid, dietCode)
    }

}
