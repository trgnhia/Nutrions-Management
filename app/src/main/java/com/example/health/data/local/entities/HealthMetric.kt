package com.example.health.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(
    tableName = "health_metric",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["uid"],
        childColumns = ["uid"],
    )]
)
@Parcelize
@IgnoreExtraProperties
data class HealthMetric (
    @JvmField
    @PropertyName("metricId")
    @PrimaryKey val metricId: String,

    @JvmField
    @PropertyName("uid")
    @ColumnInfo(name = "uid", index = true) val Uid: String,

    @JvmField
    @PropertyName("height")
    @ColumnInfo(name = "height") var Height: Float,

    @JvmField
    @PropertyName("weight")
    @ColumnInfo(name = "weight") var Weight: Float,

    @JvmField
    @PropertyName("weightTarget")
    @ColumnInfo(name = "weightTarget") var WeightTarget: Float,

    @JvmField
    @PropertyName("bmr")
    @ColumnInfo(name = "BMR") var BMR: Float,

    @JvmField
    @PropertyName("bmi")
    @ColumnInfo(name = "BMI") var BMI: Float,

    @JvmField
    @PropertyName("tdee")
    @ColumnInfo(name = "TDEE") var TDEE: Float,

    @JvmField
    @PropertyName("calorPerDay")
    @ColumnInfo(name = "CalorPerDay") var CalorPerDay : Float,

    @JvmField
    @PropertyName("restDay")
    @ColumnInfo(name = "RestDay") var RestDay : Int,

    @JvmField
    @PropertyName("updateAt")
    @ColumnInfo(name = "update_at")
    var UpdateAt: Date // cập nhật ko dùng string nữa mà dùng dạng date để gửi vào firestore cho nhanh
) : Parcelable{
    constructor() :this("", "", 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0, Date())
}
