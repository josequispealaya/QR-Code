package com.example.scanqr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scanqr.screens.FirstScreen
import com.example.scanqr.screens.QrGeneratorScreen
import com.example.scanqr.screens.QrScannerScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = AppScreens.FirstScreen.route){
        composable(route = AppScreens.FirstScreen.route){
            FirstScreen(navController)
        }

        composable(route = AppScreens.QrGeneratorScreen.route){
            QrGeneratorScreen(navController)

        }

        composable(route = AppScreens.QrScannerScreen.route){
           QrScannerScreen(navController)
        }
    }



}