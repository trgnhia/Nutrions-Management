package com.example.health.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_actions")
data class PendingAction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,   // ví dụ: "insert_metric"
    val uid: String,    // để biết user nào
    val payload: String // JSON hóa dữ liệu cần sync
)