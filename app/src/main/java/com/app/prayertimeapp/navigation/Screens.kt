package com.app.prayertimeapp.navigation

sealed class Screen(val route : String) {
    object Home : Screen("Home")
    object Qibla : Screen("Qibla")
    object More : Screen("More")
    object Signup : Screen("SignUp")

}
