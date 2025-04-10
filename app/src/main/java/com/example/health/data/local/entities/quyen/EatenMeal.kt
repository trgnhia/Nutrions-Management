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
    tableName = "eaten_meal",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["uid"],
            childColumns = ["uid"]
        )
    ]
)
data class EatenMeal(

    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    // 📅 Thời gian ghi nhận bữa ăn
    @JvmField
    @PropertyName("date")
    @ColumnInfo(name = "date")
    val Date: Date,

    // 🧑 UID người dùng (liên kết Account)
    @JvmField
    @PropertyName("uid")
    @ColumnInfo(name = "uid", index = true)
    val Uid: String,

    // 🍱 Dữ liệu dinh dưỡng
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

    // 🕐 Loại bữa ăn (Sáng, Trưa, Tối, Snack)
    @JvmField
    @PropertyName("type")
    @ColumnInfo(name = "type")
    val Type: Int

) : Parcelable {
    constructor() : this("", Date(), "", 0f, 0f, 0f, 0f, 0)
}
