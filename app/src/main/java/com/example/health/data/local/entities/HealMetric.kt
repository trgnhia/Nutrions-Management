package com.example.health.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "health_metric",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["Uid"],
        childColumns = ["Uid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HealMetric (
    @PrimaryKey val metricId: String,
    @ColumnInfo(name = "Uid", index = true) val Uid: String,
    @ColumnInfo(name = "height") val Height: Float,
    @ColumnInfo(name = "weight") val Weight: Float,
    @ColumnInfo(name = "BMR") val BMR: Float,
    @ColumnInfo(name = "BMI") val BMI: Float,
    @ColumnInfo(name = "TDEE") val TDEE: Float,
    @ColumnInfo(name = "update_at") val UpdateAt: String
)
