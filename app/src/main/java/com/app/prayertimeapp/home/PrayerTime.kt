package com.app.prayertimeapp.home

data class PrayerTime(
    val fajr : String,
    val duhr : String,
    val asar : String,
    val maghrib : String,
    val isha : String,
    val currentPrayer: String,
    val currentPrayerTime: String,
    val nextPrayerTime:String,
    val nextPrayer: String
)
