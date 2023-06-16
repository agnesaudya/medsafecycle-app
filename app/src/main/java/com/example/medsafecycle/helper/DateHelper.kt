package com.example.medsafecycle.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun dateParser(dateString: String): String {

        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = utcFormat.parse((dateString))

        val indonesiaFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        indonesiaFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

        return indonesiaFormat.format(date)
    }
}