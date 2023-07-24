package com.example.scanqr.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scanqr.services.QrCodeGeneratorHandler
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun QrGeneratorScreen(navController: NavController){
    /*AQUÍ TENEMOS QUE TOMAR UN STRING QUE INGRESA EL USUARIO PARA GENERAR EL QR*/
    /* POR AHORA ESTÁ HARDCODEADO CON www.google.com*/

    var text by remember { mutableStateOf("") }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

/*    var text by remember { mutableStateOf("") }
    TextField(value = text,
        onValueChange = {text = it} )
    Column {
        Button(onClick = {navController.popBackStack()}){
            Text(text = "Generar QR")
        }
    }*/

    text = "www.google.com"
    var url by remember { mutableStateOf(text) }

    val stringToQrCode by remember { mutableStateOf(url) }

    fun setImageBitmapQrCode() {
        imageBitmap = QrCodeGeneratorHandler.generateQrCode(stringToQrCode)
    }

    setImageBitmapQrCode()

    Surface(
        modifier = Modifier.fillMaxSize(),
        ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
              Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Generar Código QR",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(color = Color(0xFF404040))
                            .padding(20.dp)
                            .aspectRatio(1f)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(imageBitmap),
                            contentDescription = "QR Code Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                        )
                    }

                }
            }
        }
    }


    //Spacer(modifier = Modifier.height(180.dp))

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Este es tu Código QR Generado")
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Volver atrás")

        }

    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithState() {
    var text by remember { mutableStateOf("") }
    TextField(value = text,
    onValueChange = {text = it} )
}*/



