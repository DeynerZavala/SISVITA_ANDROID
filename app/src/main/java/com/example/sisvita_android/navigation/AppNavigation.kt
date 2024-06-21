package com.example.sisvita_android.navigation

import com.example.sisvita_android.ui.view.Home
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sisvita_android.ui.view.Login
import com.example.sisvita_android.ui.view.RealizarTest
import com.example.sisvita_android.ui.view.RegistrarUsuario
import com.example.sisvita_android.ui.view.TestHome

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.login.route){

        composable(route = AppScreen.login.route){
            Login(navController)
        }
        composable(route= AppScreen.home.route){
            Home(navController)
        }
        composable(route = AppScreen.registrarUsuario.route){
            RegistrarUsuario(navController)
        }
        composable(route = AppScreen.testHome.route){
            TestHome(navController)
        }
        composable(
            route = AppScreen.realizarTest.route + "/{testId}",
            arguments = listOf(navArgument("testId") { type = NavType.IntType })
        ) { backStackEntry ->
            RealizarTest(backStackEntry.arguments?.getInt("testId") ?: 0, navController)
        }
    }

}