package com.example.sisvita_android.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneId

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(isoDateString: String, pattern: String = "dd/MM/yyyy HH:mm:ss"): String {
        val zonedDateTime = ZonedDateTime.parse(isoDateString)
        val clientZoneId = ZoneId.systemDefault()
        val clientDateTime = zonedDateTime.withZoneSameInstant(clientZoneId)
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return clientDateTime.format(formatter)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String {
        val now = LocalDateTime.now()
        return now.format(DateTimeFormatter.ISO_DATE_TIME)
    }
}