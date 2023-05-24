package com.app.prayertimeapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.prayertimeapp.R
import com.app.prayertimeapp.ui.theme.onPrimary
import com.app.prayertimeapp.ui.theme.primary
import com.batoulapps.adhan.Prayer

@Composable
fun NextPrayerCard(currentPrayer: Prayer?,
                   nextPrayer: Prayer?,
                   timeForPrayer: (Prayer)-> String?
                   ){

    Card(
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(170, 245, 250, 255),
                            Color(75, 229, 238, 255)
                        ),
                        tileMode = TileMode.Clamp,
                        start = Offset.Infinite,
                        end = Offset(700f, -20f)
                    )
                )

        ){
            if (currentPrayer != null && nextPrayer != null){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Column {
                        Text(text = currentPrayer.name,
                            color = onPrimary,
                            style = MaterialTheme.typography.displayMedium,
                            fontSize = 19.sp
                        )
                        Text(text = timeForPrayer(currentPrayer)?:"",
                            color = onPrimary,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    Column() {
                        Text(text = "Next Prayer",
                            color = onPrimary,
                            style = MaterialTheme.typography.displaySmall
                        )
                        Text(text = if (currentPrayer.name == Prayer.ISHA.name){
                            timeForPrayer(Prayer.FAJR)?:""
                        }else{
                             timeForPrayer(nextPrayer)?:""
                        }
                            ,
                            color = onPrimary,
                            style = MaterialTheme.typography.displayMedium
                        )

                    }
                }

            }else{
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()

                }
            }

        }


    }
}