package com.example.health.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
@Entity(tableName = "default_exercise")
data class DefaultExercise(

    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val Id: String,

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
    val UrlImage: String

) : Parcelable {
    constructor() : this("", 0, "", 0, "","")
}
