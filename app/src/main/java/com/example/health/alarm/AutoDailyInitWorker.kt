package com.example.health.alarm

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.health.data.local.appdatabase.AppDatabase
import com.example.health.data.local.entities.TotalNutrionsPerDay
import com.example.health.data.local.entities.BurnOutCaloPerDay
import com.example.health.data.utils.IdUtil
import com.example.health.data.utils.toStartOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class AutoDailyInitWorker(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun initAllForToday() = withContext(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(context)
        val account = db.accountDao().getAccountOnce()
        val uid = account?.Uid ?: return@withContext

        val today: Date = Date().toStartOfDay() // ✅ chuẩn hóa date

        // TotalNutrionsPerDay
        val totalDao = db.totalNutrionsPerDayDao()
        if (totalDao.getByDateAndUidOnce(today, uid) == null) {
            totalDao.insert(
                TotalNutrionsPerDay(
                    id = IdUtil.generateId(),
                    Date = today, // ✅ chuẩn hóa
                    Uid = uid,
                    TotalCalo = 0.0f,
                    TotalPro = 0.0f,
                    TotalCarb = 0.0f,
                    TotalFat = 0.0f,
                    DietType = 0
                )
            )
        }

        // BurnOutCaloPerDay
        val burnDao = db.burnOutCaloPerDayDao()
        if (burnDao.getByDate(today) == null) {
            burnDao.insert(
                BurnOutCaloPerDay(
                    DateTime =  today,
                    TotalCalo = 0,
                    Uid = uid
                )
            )
        }

        // Tiếp tục thêm các bảng khác nếu cần...
    }
}
