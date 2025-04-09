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

@Parcelize
@IgnoreExtraProperties
@Entity(
    tableName = "default_diet_meal_in_plan",
//    foreignKeys = [
//        ForeignKey(
//            entity = TotalNutrionsPerDay::class,
//            parentColumns = ["id"],
//            childColumns = ["idDay"]
//        )
//    ]
)
data class DefaultDietMealInPlan(

    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val Id: String,

//    @JvmField
//    @PropertyName("idDay")
//    @ColumnInfo(name = "idDay", index = true)
//    val IdDay: Date,

    @JvmField
    @PropertyName("type")
    @ColumnInfo(name = "type")
    val Type: String,

    @JvmField
    @PropertyName("totalCalo")
    @ColumnInfo(name = "totalCalo")
    val TotalCalo: Float,

    @JvmField
    @PropertyName("totalFat")
    @ColumnInfo(name = "totalFat")
    val TotalFat: Float,

    @JvmField
    @PropertyName("totalCarb")
    @ColumnInfo(name = "totalCarb")
    val TotalCarb: Float,

    @JvmField
    @PropertyName("totalProtein")
    @ColumnInfo(name = "totalProtein")
    val TotalProtein: Float,

    @JvmField
    @PropertyName("urlImage")
    @ColumnInfo(name = "urlImage")
    val UrlImage: String

) : Parcelable {
    constructor() : this("", "", 0f, 0f, 0f, 0f,"")
}
