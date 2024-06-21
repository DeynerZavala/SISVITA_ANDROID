package com.example.sisvita_android.navigation

sealed class AppScreen(val route: String){
    object login: AppScreen("login")
    object home : AppScreen("home")
    object registrarUsuario : AppScreen("registrarUsuario")
    object  testHome: AppScreen("testHome")
    object realizarTest: AppScreen("realizarTest")
    object evaluarTest: AppScreen("evaluarTest")
    object mapaDeCarlor: AppScreen("mapaDeCalor")
    object vigilancia: AppScreen("vigilancia")
}
