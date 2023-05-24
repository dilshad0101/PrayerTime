package com.app.prayertimeapp.home

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.prayertimeapp.navigation.HomeNavBar
import com.app.prayertimeapp.R
import com.app.prayertimeapp.navigation.Screen
import com.app.prayertimeapp.ui.theme.onPrimary
import com.app.prayertimeapp.ui.theme.primary
import com.app.prayertimeapp.ui.theme.scrime
import com.app.prayertimeapp.util.formatDateToTime
import com.batoulapps.adhan.PrayerTimes


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    prayerTime: PrayerTimes?,
    currentLocality: (Context) -> String
){
    val context = LocalContext.current


    @Composable
    fun Cell(string: String, color: Color = onPrimary){
        Text(text = string, style = MaterialTheme.typography.bodyMedium, color = color)
    }
    var isLoading by remember{ mutableStateOf(false) }

    isLoading = prayerTime == null

    AnimatedContent(targetState = isLoading) { _isLoading ->
        if(
            _isLoading
        ){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }

        }else{
            Scaffold(
                containerColor = primary,
                contentColor = onPrimary,
                bottomBar = { HomeNavBar(navController = navController) },
                modifier = Modifier.background(
                    Brush.linearGradient(
                    listOf(
                        Color(0xff8e9eab),
                        Color(0xffeef2f3)
                    ),
                    tileMode = TileMode.Clamp,
                    start = Offset.Infinite,
                    end = Offset(700f, -20f)
                ))
            ) { it ->

                Column(modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 10.dp,
                        bottom = it.calculateBottomPadding()
                    )
                    .fillMaxSize()
                    .background(primary)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Column() {
                            Text(text = stringResource(id = R.string.home_screen_top1),
                                color = scrime,
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(text = currentLocality.invoke(LocalContext.current),
                                color = onPrimary,
                                style = MaterialTheme.typography.headlineMedium,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.Signup.route)
                                }
                            )
                        }
                        IconButton(onClick = { navController.navigate(Screen.Signup.route) }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_location_on_24),"Change your location")
                        }

                    }

                    Spacer(modifier = Modifier
                        .height(24.dp)
                        .fillMaxWidth())

                    NextPrayerCard(
                        currentPrayer = prayerTime?.currentPrayer(),
                        nextPrayer = prayerTime?.nextPrayer())
                    { prayer->
                        if (prayerTime!=null){
                            if (prayerTime.timeForPrayer(prayer)!= null){
                                formatDateToTime(prayerTime.timeForPrayer(prayer))
                            }else{
                                null
                            }
                        }else{
                            null
                        }


                    }
                    Spacer(modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth())
                    if (prayerTime != null){
                        Column(modifier = Modifier.fillMaxSize()) {
                            Column(verticalArrangement = Arrangement.spacedBy(20.dp),modifier = Modifier.fillMaxSize()) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Cell("Prayer", scrime)
                                    Cell("Adhan", scrime)
                                    Cell("Iqama", scrime)
                                }
                                Row(horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    if (prayerTime.fajr != null){
                                        Cell("Fajr")
                                        Cell(formatDateToTime(prayerTime.fajr))
                                        Cell(formatDateToTime(prayerTime.fajr))
                                    }

                                }
                                Row(horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    if (prayerTime.dhuhr != null){
                                        Cell("Dhuhr")
                                        Cell(formatDateToTime(prayerTime.dhuhr))
                                        Cell(formatDateToTime(prayerTime.dhuhr))
                                    }

                                }
                                Row(horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    if (prayerTime.asr!= null){
                                        Cell("Asr")
                                        Cell(formatDateToTime(prayerTime.asr))
                                        Cell(formatDateToTime(prayerTime.asr))
                                    }

                                }
                                Row(horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    if (prayerTime.maghrib!= null){
                                        Cell("Maghrib")
                                        Cell(formatDateToTime(prayerTime.maghrib))
                                        Cell(formatDateToTime(prayerTime.maghrib))
                                    }

                                }
                                Row(horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    if (prayerTime.isha!=null){
                                        Cell("Isha")
                                        Cell(formatDateToTime(prayerTime.isha))
                                        Cell(formatDateToTime(prayerTime.isha))
                                    }

                                }
                            }
                        }
                    }
                    else{
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            CircularProgressIndicator()
                        }
                    }



                }
            }
        }
    }


}

