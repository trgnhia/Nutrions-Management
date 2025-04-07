package com.example.health.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "account")
data class Account(
    @JvmField
    @PropertyName("uid")
    @ColumnInfo(name = "uid")
    @PrimaryKey() val Uid : String,

    @JvmField
    @PropertyName("name")
    @ColumnInfo(name = "name") val Name: String,

    @JvmField
    @PropertyName("email")
    @ColumnInfo(name = "email") val Email: String,

    @JvmField
    @PropertyName("status")
    @ColumnInfo(name = "Status") var Status: String
){
    constructor() : this("", "", "", "")
}
