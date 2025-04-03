package com.example.health.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "base_info")
data class BaseInfo(
    @JvmField
    @PropertyName("uid")
    @PrimaryKey() val Uid : String,

    @JvmField
    @PropertyName("name")
    @ColumnInfo(name = "name") val Name: String,

    @JvmField
    @PropertyName("age")
    @ColumnInfo(name = "age")  val Age: Int,

    @JvmField
    @PropertyName("height")
    @ColumnInfo(name = "height") var Height: Float,

    @JvmField
    @PropertyName("weight")
    @ColumnInfo(name = "weight") var Weight: Float,

    @JvmField
    @PropertyName("gender")
    @ColumnInfo(name = "gender") val Gender: String,

    @JvmField
    @PropertyName("activityLevel")
    @ColumnInfo(name = "activiy_level") var ActivityLevel: Int,

    @JvmField
    @PropertyName("isDiet")
    @ColumnInfo(name = "is_diet", defaultValue = "0") var IsDiet: Int = 0

){
    constructor() : this("", "", 0, 0f, 0f, "", 0,0)
}
