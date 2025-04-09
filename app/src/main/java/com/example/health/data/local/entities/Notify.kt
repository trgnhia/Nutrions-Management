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
    tableName = "Notify",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["uid"],
        childColumns = ["uid"],
    )]
)
data class Notify(
    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @JvmField
    @PropertyName("uid")
    @ColumnInfo(name = "uid", index = true) val Uid: String,

    @JvmField
    @PropertyName("message")
    @ColumnInfo(name = "message") val Message : String,

    @JvmField
    @PropertyName("notifyTime")
    @ColumnInfo(name = "notifyTime") val NotifyTime : Date
) : Parcelable {
    constructor() : this("", "", "", Date()) // constructor mặc định cho Firebase
}

