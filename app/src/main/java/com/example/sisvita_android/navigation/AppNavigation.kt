package com.example.sisvita_android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sisvita_android.ui.view.Login
import com.example.sisvita_android.navigation.AppScreen
import com.example.sisvita_android.ui.view.RegistrarUsuario
import com.example.sisvita_android.ui.view.TestHome
import com.example.sisvita_android.ui.viewmodel.LoginViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.login.route){

        composable(route = AppScreen.login.route){
            Login(navController)
        }
        composable(route= AppScreen.testHome.route){
            TestHome(navController)
        }
        composable(route = AppScreen.registrarUsuario.route){
            RegistrarUsuario(navController)
        }

    }

}