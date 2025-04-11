package com.example.health.data.local.entities


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
@Entity(
    tableName = "custom_exercise",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["uid"],
            childColumns = ["uid"]
        )
    ]
)
data class CustomExercise(

    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @JvmField
    @PropertyName("caloBurn")
    @ColumnInfo(name = "caloBurn")
    val CaloBurn: Int,

    @JvmField
    @PropertyName("unitType")
    @ColumnInfo(name = "unitType")
    val UnitType: String,

    @JvmField
    @PropertyName("unit")
    @ColumnInfo(name = "unit")
    val Unit: Int,

    @JvmField
    @PropertyName("name")
    @ColumnInfo(name = "name")
    val Name: String,

    @JvmField
    @PropertyName("urlImage")
    @ColumnInfo(name = "urlImage")
    val UrlImage: String,

    @JvmField
    @PropertyName("uid")
    @ColumnInfo(name = "uid", index = true)
    val Uid: String

) : Parcelable {
    constructor() : this("", 0, "", 0, "", "", "")
}

