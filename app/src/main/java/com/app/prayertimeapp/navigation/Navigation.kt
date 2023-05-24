package com.app.prayertimeapp.navigation

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.prayertimeapp.R
import com.app.prayertimeapp.data.getSharedPref
import com.app.prayertimeapp.data.saveToSharedPref
import com.app.prayertimeapp.home.HomeScreen
import com.app.prayertimeapp.signup.RegionSelection
import com.app.prayertimeapp.signup.getCoordinates
import com.app.prayertimeapp.ui.theme.onPrimary
import com.app.prayertimeapp.ui.theme.primary
import com.app.prayertimeapp.ui.theme.scrime
import com.app.prayertimeapp.util.CountryCityData
import com.batoulapps.adhan.PrayerTimes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Navigation(
    prayerTime: PrayerTimes?
){
    val context = LocalContext.current

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination =
            if(getSharedPref(context) != null){
                if(getSharedPref(context)?.first != null){
                     Screen.Home.route
                }else{
                    Screen.Signup.route
                }
            }
            else{
                 Screen.Signup.route
            }


    ){
        composable(Screen.Home.route){
            HomeScreen(navController,
                prayerTime = prayerTime,
                currentLocality = {
                    val values = getSharedPref(it,1)
                    val currentLocality = "${values?.second},${values?.first}"
                    currentLocality
                }
            )
        }
        composable(Screen.Qibla.route){

        }
        composable(Screen.More.route){

        }
        composable(Screen.Signup.route){
            val scope = rememberCoroutineScope()
            val countryCityData = CountryCityData(LocalContext.current)

            RegionSelection(navController = navController,
                onRegionSubmit = { country ,city ->
                    saveToSharedPref(context,country,city,1)

                    scope.launch(Dispatchers.IO) {
                        val coordinates = getCoordinates(country, city)
                        if (coordinates != null) {
                            val latitude = coordinates.first
                            val longitude = coordinates.second

                            saveToSharedPref(context,latitude.toString(),longitude.toString())

                            Log.d("1122 check",
                                "Coordinates for $city, $country: Latitude=$latitude, Longitude=$longitude")
                            withContext(Dispatchers.Main){
                                navController.navigate(Screen.Home.route)

                            }
                        } else {
                            Log.d("1122 check","Unable to retrieve coordinates for $city, $country")
                        }
                    }
                }
                )
        }
    }

}



