package com.vinayakgardi.notes.util

import java.text.SimpleDateFormat
import java.util.Date

object Converter {
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd  HH:mm")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }
}