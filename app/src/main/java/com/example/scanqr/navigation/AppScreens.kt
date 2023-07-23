package com.example.scanqr.navigation

sealed class AppScreens(val route: String){
    object FirstScreen: AppScreens("first_Screen")
    object QrGeneratorScreen: AppScreens("qr_Generator_Screen")
    object QrScannerScreen: AppScreens("qr_Scanner_Screen")
}
