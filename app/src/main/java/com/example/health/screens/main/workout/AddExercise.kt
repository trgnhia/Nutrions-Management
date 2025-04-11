

package com.example.health.screens.main.workout
import androidx.lifecycle.viewModelScope
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.local.entities.ExerciseLog
import com.example.health.data.utils.toStartOfDay
import com.example.health.data.local.viewmodel.BurnOutCaloPerDayViewModel
import com.example.health.data.local.viewmodel.ExerciseLogViewModel
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

fun AddExercise(
    uid: String,
    burnOutCaloPerDayViewModel: BurnOutCaloPerDayViewModel,
    exerciseLogViewModel: ExerciseLogViewModel,
    today: Date, // đã được chuẩn hóa từ trước
    exerciseID: String,
    caloBurn: Int,
    unitType: String,
    unit: Int,
    name: String,
) {
    exerciseLogViewModel.viewModelScope.launch {
        // 1. Kiểm tra xem đã có bản ghi tổng calo đốt trong ngày chưa
        var total = burnOutCaloPerDayViewModel.getByDate(today)

        // 2. Nếu chưa có → tạo mới
        if (total == null) {
            val newTotal = BurnOutCaloPerDay(
                DateTime = today,
                TotalCalo = caloBurn,
                Uid = uid

            )
            burnOutCaloPerDayViewModel.insert(newTotal)
            total = newTotal
        }

        // 3. Tạo bản ghi ExerciseLog cho buổi tập
        val log = ExerciseLog(
            id = UUID.randomUUID().toString(),
            IdExercise = exerciseID,
            CaloBurn = caloBurn,
            UnitType = unitType,
            Unit = unit,
            Name = name,
            DateTime = today,
            Uid = uid
        )
        exerciseLogViewModel.insert(log)

        // 4. Cập nhật lại bảng BurnOutCaloPerDay bằng cách cộng thêm calo mới
        val updatedTotal = total.copy(
            TotalCalo = total.TotalCalo+ caloBurn
        )
        burnOutCaloPerDayViewModel.update(updatedTotal)
    }
}


