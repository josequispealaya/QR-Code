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
import com.example.scanqr.navigation.AppScreens

@Composable


fun QrGeneratorScreen(navController: NavController){
    QrGeneratorBodyContent(navController)
}

@Composable
fun QrGeneratorBodyContent(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Esta es la pantalla que va a generar el QR")
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Volver atrás")

        }

    }
}