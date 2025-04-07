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
    @PropertyName("caloPerHour")
    @ColumnInfo(name = "caloPerHour")
    val CaloPerHour: Int,

    @JvmField
    @PropertyName("name")
    @ColumnInfo(name = "name")
    val Name: String

) : Parcelable {
    constructor() : this("", 0, "")
}
