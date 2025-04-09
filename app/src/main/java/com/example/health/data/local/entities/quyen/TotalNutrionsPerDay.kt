package com.example.health.data.local.entities.quyen

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.health.data.local.entities.Account
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@IgnoreExtraProperties
@Entity(
    tableName = "total_nutrions_per_day",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["uid"],
            childColumns = ["uid"]
        )
    ]
)
data class TotalNutrionsPerDay(

    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val Id: Date,

    @JvmField
    @PropertyName("totalCalo")
    @ColumnInfo(name = "totalCalo")
    val TotalCalo: Float,

    @JvmField
    @PropertyName("totalPro")
    @ColumnInfo(name = "totalPro")
    val TotalPro: Float,

    @JvmField
    @PropertyName("totalCarb")
    @ColumnInfo(name = "totalCarb")
    val TotalCarb: Float,

    @JvmField
    @PropertyName("totalFat")
    @ColumnInfo(name = "totalFat")
    val TotalFat: Float,

    @JvmField
    @PropertyName("uid")
    @ColumnInfo(name = "uid", index = true)
    val Uid: String,

    @JvmField
    @PropertyName("dietType")
    @ColumnInfo(name = "dietType")
    val DietType: Int

) : Parcelable {
    constructor() : this(Date(), 0f, 0f, 0f, 0f, "", 0)
}
