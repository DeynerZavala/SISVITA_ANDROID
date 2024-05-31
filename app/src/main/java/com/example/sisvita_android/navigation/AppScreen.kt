package com.example.sisvita_android.navigation

sealed class AppScreen(val route: String){
    object login: AppScreen("login")
    object testHome : AppScreen("testHome")
}
