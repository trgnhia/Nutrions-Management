package com.example.health.data.utils

fun Float.to1DecimalString(): String = "%.1f".format(this)

fun Float.to2DecimalString(): String = "%.2f".format(this)
fun Float.to0DecimalString(): String = String.format("%.0f", this)