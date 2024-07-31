package com.example.scanqr.screens

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.scanqr.R
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView


@Composable
fun QrScannerScreen(navController: NavController) {
    var stringFromQrCode by remember { mutableStateOf("") }
    var isPopupVisible by remember { mutableStateOf(false) }
    var isScanningEnabled by remember { mutableStateOf(true) }

    val buttonTextColor = colorResource(id = R.color.button_text_color)
    val buttonColor = colorResource(id = R.color.button_color)

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
            if (!granted) {
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
            if (stringFromQrCode.startsWith("http://") || stringFromQrCode.startsWith("https://") || stringFromQrCode.startsWith("www.")) {
                AlertDialog(
                    onDismissRequest = {
                        isPopupVisible = false
                        isScanningEnabled = true
                    },
                    title = { Text(text = "Código QR escaneado") },
                    text = { Text(text = "$stringFromQrCode\n ¿Deseas ir a la página web?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                try {
                                    //agregamos el prefijo https:// si el link no lo trae
                                    var formattedUrl = stringFromQrCode
                                    if (formattedUrl.startsWith("www.")) {
                                        formattedUrl = "https://$formattedUrl"
                                    }
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl))
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    e.printStackTrace()
                                }
                                isPopupVisible = false
                                isScanningEnabled = true
                            }
                        ) {
                            Text(text = "Sí")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                isPopupVisible = false
                                isScanningEnabled = true
                            }
                        ) {
                            Text(text = "No")
                        }
                    }
                )
            } else {
                AlertDialog(
                    onDismissRequest = {
                        isPopupVisible = false
                        isScanningEnabled = true
                    },
                    title = { Text(text = "Código Escaneado") },
                    text = { Text(text = stringFromQrCode) },
                    confirmButton = {
                        Button(
                            onClick = {
                                isPopupVisible = false
                                isScanningEnabled = true
                            }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(buttonColor)
        ) {
            Text(
                text = stringResource(R.string.ss_back_text),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp
                ),
                color = buttonTextColor
            )
        }
    }
}


