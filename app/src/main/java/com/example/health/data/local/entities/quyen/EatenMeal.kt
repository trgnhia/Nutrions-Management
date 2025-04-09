package com.example.health.data.local.entities.quyen

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
    tableName = "eaten_meal",
    foreignKeys = [
        ForeignKey(
            entity = TotalNutrionsPerDay::class,
            parentColumns = ["id"],
            childColumns = ["idDay"]
        )
    ]
)
data class EatenMeal(

    @JvmField
    @PropertyName("idDay")
    @PrimaryKey
    @ColumnInfo(name = "idDay")
    val IdDay: Date,

    @JvmField
    @PropertyName("totalCalos")
    @ColumnInfo(name = "totalCalos")
    val TotalCalos: Float,

    @JvmField
    @PropertyName("totalFats")
    @ColumnInfo(name = "totalFats")
    val TotalFats: Float,

    @JvmField
    @PropertyName("totalCarbs")
    @ColumnInfo(name = "totalCarbs")
    val TotalCarbs: Float,

    @JvmField
    @PropertyName("totalPro")
    @ColumnInfo(name = "totalPro")
    val TotalPro: Float,

    @JvmField
    @PropertyName("type")
    @ColumnInfo(name = "type")
    val Type: String

) : Parcelable {
    constructor() : this(Date(), 0f, 0f, 0f, 0f, "")
}
