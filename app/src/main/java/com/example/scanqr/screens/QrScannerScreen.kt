package com.example.scanqr.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView


@Composable
fun QrScannerScreen(navController: NavController) {
    var stringFromQrCode by remember { mutableStateOf("") }
    var isPopupVisible by remember { mutableStateOf(false) }
    var isScanningEnabled by remember { mutableStateOf(true) }

    val context = LocalContext.current

    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
            if (!granted)
                run {
                    navController.popBackStack()
                }
        }
    )

    LaunchedEffect(key1 = true) {
        if (!hasCamPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (hasCamPermission) {
            AndroidView(
                factory = {
                    CompoundBarcodeView(context).apply {
                        val capture = CaptureManager(context as Activity, this)
                        capture.initializeFromIntent(context.intent, null)
                        this.setStatusText("")
                        capture.decode()
                        this.decodeContinuous { result ->
                            if (isScanningEnabled) {
                                result.text?.let { barCodeOrQr ->
                                    stringFromQrCode = barCodeOrQr
                                    Log.d("QrScanner", stringFromQrCode)
                                    isPopupVisible = true
                                    isScanningEnabled = false
                                }
                            }
                        }
                        this.resume()
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }

        if (isPopupVisible) {
            AlertDialog(
                onDismissRequest = { isPopupVisible = false },
                title = { Text(text = "Código Escaneado") },
                text = { Text(text = stringFromQrCode) },
                confirmButton = {
                    Button(
                        onClick = {
                            //navController.navigate(Uri.parse(stringFromQrCode))
                            //val webpage = Uri.parse(stringFromQrCode)
                            //val context = LocalContext
                            //val permissionState = rememberPermissionState(Manifest.permission.INTERNET)
                            //val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
                               // if (true) {
                                  //  val intent = Intent(Intent.ACTION_VIEW, webpage)
                                  //  context.startActivity(intent)
                               // }

                        }
                    ) {
                        Text(text = "los permisos para ir a una página externa al hacer clik en el código no están funcionando")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                                  isPopupVisible = false
                                  isScanningEnabled = true
                        }
                    ) {
                        Text(text = "Close")
                    }
                }

            )
        }
    }

    //Spacer(modifier = Modifier.height(80.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Este es tu QR Escabeado")
        Button(modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Volver atrás")

        }
    }
}


