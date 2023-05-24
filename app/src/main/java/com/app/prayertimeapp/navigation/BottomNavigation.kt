package com.app.prayertimeapp.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.prayertimeapp.R
import com.app.prayertimeapp.ui.theme.onPrimary
import com.app.prayertimeapp.ui.theme.primary
import com.app.prayertimeapp.ui.theme.scrime

data class BottomNavItem(
    val name: String,
    val route: String,
    @DrawableRes val icon:  Int,
)
@Composable
fun HomeNavBar(navController: NavController){

    BottomNavBarModel(
        items = listOf(
            BottomNavItem(
                name = "Home",
                route = Screen.Home.route,
                icon = R.drawable.round_home_24

            ),
            BottomNavItem(
                name = "Qibla",
                route = Screen.Qibla.route,
                icon = R.drawable.baseline_broadcast_on_personal_24

            ),
            BottomNavItem(
                name = "More",
                route = Screen.More.route,
                icon = R.drawable.baseline_menu_24

            ),
        ),
        navController = navController ,
        onItemClick ={
            navController.navigate(it.route)
        }
    )
}




@Composable
fun BottomNavBarModel(
    items: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit,
) {

    NavigationBar(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .shadow(1.dp, spotColor = Color.LightGray),
        containerColor = primary,
        contentColor = onPrimary,
        tonalElevation = 10.dp
    ) {
        val backStackEntry =  navController.currentBackStackEntryAsState()
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = onPrimary,
                    unselectedIconColor = scrime,
                    indicatorColor = primary
                ),
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painterResource(id = item.icon), contentDescription = null)

                        if (selected) {
                            Text(text = item.name, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                },
            )
        }
    }
}
