package com.example.health.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.health.data.local.entities.Account
import com.google.firebase.firestore.PropertyName

@Entity(
    tableName = "macro",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["uid"],
        childColumns = ["uid"],
    )]
)
data class Macro(
    @JvmField
    @PropertyName("uid")
    @PrimaryKey
    @ColumnInfo(name = "uid", index = true) val Uid: String,

    @JvmField
    @PropertyName("calo")
    @ColumnInfo(name = "calo") var Calo: Float,

    @JvmField
    @PropertyName("protein")
    @ColumnInfo(name = "protein") var Protein: Float,

    @JvmField
    @PropertyName("fat")
    @ColumnInfo(name = "fat") var Fat: Float,

    @JvmField
    @PropertyName("carb")
    @ColumnInfo(name = "carb") var Carb: Float,

    @JvmField
    @PropertyName("tdee")
    @ColumnInfo(name = "TDEE") var TDEE: Float,

){
    constructor() :this("", 0f, 0f, 0f, 0f, 0f)
}
