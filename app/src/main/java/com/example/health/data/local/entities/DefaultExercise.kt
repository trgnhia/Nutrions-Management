package com.example.health.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
@Entity(tableName = "default_exercise")
data class DefaultExercise(
    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id") val Id: String,

    @JvmField
    @PropertyName("calo_per_hour")
    @ColumnInfo(name = "calo_per_hour") val CaloPerHour: Int
) : Parcelable {
    constructor() : this("", 0)
}
