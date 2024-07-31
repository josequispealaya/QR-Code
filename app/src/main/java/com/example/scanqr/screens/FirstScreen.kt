package com.example.scanqr.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.scanqr.navigation.AppScreens
import com.example.scanqr.R

@Composable
fun FirstScreen(navController: NavController) {
    BodyContent(navController)
}

@Composable
fun BodyContent(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? Activity

    // Colores
    val backgroundBannerColor = colorResource(id = R.color.fs_background_banner)
    val backgroundScreenColor = colorResource(id = R.color.fs_background_first_screen)
    val buttonColor = colorResource(id = R.color.button_color)
    val buttonTextColor = colorResource(id = R.color.button_text_color)
    val subTitleTextColor = colorResource(id = R.color.fs_sub_title_text_color)

    // Strings
    val subtitleBanner = stringResource(id = R.string.fs_sub_title)
    val subtitle2 = stringResource(id = R.string.fs_sub_title2)
    val subtitle3 = stringResource(id = R.string.fs_sub_title3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundScreenColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        //UTNBanner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundBannerColor)
                .padding(vertical = 20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aro_logo_utn),
                    contentDescription = "UTN Logo",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "UTN frba", style = TextStyle(
                        fontSize = 36.sp, color = Color.White, fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            // Subtítulo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = subtitleBanner,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 2.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        //Sub título 2 - Scanear
        Text(
            text = subtitle2,
            fontWeight = FontWeight.Bold,
            color = subTitleTextColor,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Botón "Scanear"
        Button(
            onClick = {
                navController.navigate(route = AppScreens.QrScannerScreen.route)
            },
            modifier = Modifier
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(buttonColor)

        ) {
            Text(
                text = stringResource(R.string.fs_button_scan),
                style = MaterialTheme.typography.bodyLarge.copy(

                    fontSize = 20.sp
                ),
                color = buttonTextColor
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Sub título 3 - Generar
        Text(
            text = subtitle3,
            fontWeight = FontWeight.Bold,
            color = subTitleTextColor,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Botón "Generar"
        Button(
            onClick = {
                navController.navigate(route = AppScreens.QrGeneratorScreen.route)
            },
            modifier = Modifier
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(buttonColor)

        ) {
            Text(
                text = stringResource(R.string.fs_button_generate),
                style = MaterialTheme.typography.bodyLarge.copy(

                    fontSize = 20.sp
                ),
                color = buttonTextColor
            )
        }

        // ubicamos el botón salir abajo de los demás
        Spacer(modifier = Modifier.weight(1f))

        // Botón "Salir"
        Button(
            onClick = {
                activity?.finishAffinity()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(buttonColor)
        ) {
            Text(
                text = stringResource(R.string.fs_button_exit),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 21.sp
                ),
                color = buttonTextColor
            )
        }
    }
}






