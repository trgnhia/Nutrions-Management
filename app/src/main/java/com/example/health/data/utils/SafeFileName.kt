package com.example.health.data.utils

fun String.toSafeFileName(): String {
    return this.lowercase().replace(" ", "_").replace(Regex("[^a-zA-Z0-9_]"), "")
}
