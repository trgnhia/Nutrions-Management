package com.example.health.data.utils


import java.util.UUID

object IdUtil {
    // ✅ Hàm tạo ID ngẫu nhiên
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}
