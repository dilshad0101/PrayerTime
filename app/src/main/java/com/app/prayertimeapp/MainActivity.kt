package com.app.prayertimeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.app.prayertimeapp.data.getSharedPref
import com.app.prayertimeapp.navigation.Navigation
import com.app.prayertimeapp.ui.theme.PrayerTimeAppTheme
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext
        val coordinates = getSharedPref(context)?.first?.let { getSharedPref(context)?.second?.let { it1 -> Coordinates(it.toDouble(), it1.toDouble()) } }
        val date = DateComponents(2023, 4, 15)
        val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        params.madhab = Madhab.HANAFI; params.adjustments.fajr = 2
        var prayerTime:PrayerTimes? = null

        lifecycleScope.launch {
            if(coordinates != null) {
                prayerTime = PrayerTimes(coordinates, date, params)
            }
        }

        setContent {
            PrayerTimeAppTheme {
                Navigation(prayerTime)
            }
        }
    }

}


