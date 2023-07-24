package com.example.scanqr.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scanqr.navigation.AppScreens

@Composable
fun FirstScreen(navController: NavController){

            BodyContent(navController)
    }

@Composable
fun BodyContent(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),
                      verticalArrangement = Arrangement.Center,
                      horizontalAlignment = Alignment.CenterHorizontally
          ) {
        Text("Scanea tu Código QR")
        Button(onClick = {
            navController.navigate(route = AppScreens.QrScannerScreen.route)
        }) {
            Text(text = "Scanear")

        }

        Spacer(modifier = Modifier.height(60.dp))

        Text("Genera tu Código QR")
        Button(onClick = {
            navController.navigate(route = AppScreens.QrGeneratorScreen.route)
        }) {
            Text(text = "Generar QR")

        }

        Spacer(modifier = Modifier.height(60.dp))

        Button(onClick = {
                val activity = LocalContext as Activity
                activity.finish()
        },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(text = "Salir")

        }
    }
}






