package com.example.health.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey() val Uid : String,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "email") val Email: String,
    @ColumnInfo(name = "Status") var Status: String
)
