package com.example.health.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@IgnoreExtraProperties
@Entity(
    tableName = "burn_out_calo_per_day",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["uid"],
        childColumns = ["uid"]
    )]
)

data class BurnOutCaloPerDay(
    @JvmField
    @PropertyName("dateTime")
    @PrimaryKey
    @ColumnInfo(name = "dateTime") val DateTime: Date,

    @JvmField
    @PropertyName("totalCalo")
    @ColumnInfo(name = "totalCalo") val TotalCalo: Int,

    @JvmField
    @PropertyName("uid")
    @ColumnInfo(name = "uid", index = true) val Uid: String
) : Parcelable {
    constructor() : this(Date(), 0, "")
}
