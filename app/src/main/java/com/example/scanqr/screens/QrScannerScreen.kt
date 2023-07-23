package com.example.scanqr.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun QrScannerScreen(navController: NavController){

    QrScannerBodyContent(navController)

}

@Composable
fun QrScannerBodyContent(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Esta es la pantalla que va Escanear el QR")
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Volver atrás")

        }
    }
}