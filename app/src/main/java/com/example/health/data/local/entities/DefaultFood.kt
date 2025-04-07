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
@Entity(tableName = "default_food")
data class DefaultFood(

    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val Id: String,

    @JvmField
    @PropertyName("name")
    @ColumnInfo(name = "name")
    val Name: String,

    @JvmField
    @PropertyName("calo")
    @ColumnInfo(name = "calo")
    val Calo: Float,

    @JvmField
    @PropertyName("fat")
    @ColumnInfo(name = "fat")
    val Fat: Float,

    @JvmField
    @PropertyName("carb")
    @ColumnInfo(name = "carb")
    val Carb: Float,

    @JvmField
    @PropertyName("protein")
    @ColumnInfo(name = "protein")
    val Protein: Float,

    @JvmField
    @PropertyName("type")
    @ColumnInfo(name = "type")
    val Type: Int,

    @JvmField
    @PropertyName("quantity")
    @ColumnInfo(name = "quantity")
    val Quantity: Int,

    @JvmField
    @PropertyName("quantityType")
    @ColumnInfo(name = "quantityType")
    val QuantityType: String

) : Parcelable {
    constructor() : this("", "", 0f, 0f, 0f, 0f, 0, 0, "")
}
