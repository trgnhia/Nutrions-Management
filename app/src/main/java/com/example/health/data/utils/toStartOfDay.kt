package com.example.health.data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toStartOfDay(): Date {
    return Date.from(
        this.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
    )
}
