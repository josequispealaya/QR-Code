package com.example.scanqr.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.scanqr.R
import com.example.scanqr.services.QrCodeGeneratorHandler
import java.io.IOException


@Composable
fun QrGeneratorScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val saveFileOk = stringResource(R.string.gs_save_file_ok)
    val saveFileNoOk = stringResource(R.string.gs_save_file_no_ok)
    val fileIsNull = stringResource(R.string.gs_save_file_is_null)
    val saveFileFailed = stringResource(R.string.gs_save_file_failed)
    val qrCodeNoOk = stringResource(R.string.gs_qr_code_no_ok)


    val buttonTextColor = colorResource(id = R.color.button_text_color)
    val buttonColor = colorResource(id = R.color.button_color)

    fun saveQrCodeToFile(context: Context, bitmap: Bitmap, uri: Uri?) {
        if (uri == null) return

        try {
            context.contentResolver.openOutputStream(uri).use { outputStream ->
                if (outputStream != null) {
                    val success = bitmap.compress(CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                    //outputStream.close()
                    if (success) {
                        Toast.makeText(context, saveFileOk, Toast.LENGTH_LONG).show()

                        // Reset the screen and input text after saving
                        text = ""
                        imageBitmap = null
                    } else {
                        Toast.makeText(
                            context, saveFileNoOk , Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(context, fileIsNull, Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, saveFileFailed, Toast.LENGTH_LONG).show()
        }
    }

    val saveQrCodeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("image/png")
    ) { uri ->
        imageBitmap?.let { saveQrCodeToFile(context, it, uri) }
    }

    DisposableEffect(Unit) {
        onDispose {
            imageBitmap?.recycle()
            imageBitmap = null
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(R.string.gs_title_qr_generate),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(stringResource(R.string.gs_enter_text)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                //Botón Superior Generar QR
                Button(
                    onClick = {
                        keyboardController?.hide() // Ocultar teclado
                        if (text.isNotEmpty()) {
                            try {
                                imageBitmap = QrCodeGeneratorHandler.generateQrCode(text)
                                if (imageBitmap == null) {
                                    Toast.makeText(context, qrCodeNoOk, Toast.LENGTH_LONG).show()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(context, qrCodeNoOk, Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, fileIsNull, Toast.LENGTH_LONG).show()
                        }
                    },

                    modifier = Modifier.padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(buttonColor)
                ) {
                    Text(
                        text = stringResource(R.string.gs_qr_generate),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp
                        ),
                        color = buttonTextColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(color = Color(0xFF404040))
                        .padding(20.dp)
                        .aspectRatio(1f)
                ) {
                    imageBitmap?.let {
                        Image(
                            painter = rememberAsyncImagePainter(imageBitmap),
                            contentDescription = stringResource(R.string.gs_qr_code_image),
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(buttonColor)
                    ) {
                        Text(
                            text = stringResource(R.string.gs_back),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp
                            ),
                            color = buttonTextColor
                        )
                    }

                    Button(
                        onClick = {
                            imageBitmap?.let {
                                saveQrCodeLauncher.launch("${System.currentTimeMillis()}.png")
                            } ?: Toast.makeText(context, qrCodeNoOk , Toast.LENGTH_LONG)
                                .show()
                        },
                        modifier = Modifier.padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(buttonColor)
                    ) {
                        Text(
                            text = stringResource(R.string.gs_save),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp
                            ),
                            color = buttonTextColor
                        )
                    }
                }
            }
        }
    }
}

