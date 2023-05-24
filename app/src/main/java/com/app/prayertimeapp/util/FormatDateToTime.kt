package com.app.prayertimeapp.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun formatDateToTime(date: Date): String {
    // Convert java.util.Date to java.time.Instant
    val instant = date.toInstant()

    // Convert java.time.Instant to java.time.LocalDateTime in the default time zone
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Extract java.time.LocalTime portion from java.time.LocalDateTime
    val localTime = localDateTime.toLocalTime()

    // Format java.time.LocalTime to string in the format "hh:mm a"
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return localTime.format(formatter)
}