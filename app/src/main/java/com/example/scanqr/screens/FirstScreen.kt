package com.example.scanqr.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.modifier.modifierLocalConsumer
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
    }
}



