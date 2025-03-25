package com.example.health.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "base_info")
data class BaseInfo(
    @PrimaryKey() val Uid : String,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "age")  val Age: Int,
    @ColumnInfo(name = "height") val Height: Float,
    @ColumnInfo(name = "weight") val Weight: Float,
    @ColumnInfo(name = "goal") val Goal: String,
    @ColumnInfo(name = "gender") val Gender: String,
    @ColumnInfo(name = "activiy_level") val ActivityLevel: String,
    @ColumnInfo(name = "goal_achivement") val GoalAchivement: Float
)
