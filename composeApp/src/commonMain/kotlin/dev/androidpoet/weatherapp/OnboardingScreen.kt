package dev.androidpoet.weatherapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import org.jetbrains.compose.resources.Font
import weather_app.composeapp.generated.resources.Res
import weather_app.composeapp.generated.resources.background_image
import weather_app.composeapp.generated.resources.opensans_bold
import weather_app.composeapp.generated.resources.poppins_bold
import weather_app.composeapp.generated.resources.poppins_regular
import weather_app.composeapp.generated.resources.weather_logo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OnboardingScreen() {

    val poppins = FontFamily(
        Font(Res.font.poppins_bold, weight = FontWeight.Bold)
    )

    val poppinsRegular = FontFamily(
        Font(Res.font.poppins_regular, weight = FontWeight.Normal)
    )

    val openSans = FontFamily(
        Font(Res.font.opensans_bold, weight = FontWeight.Bold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.background_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(Res.drawable.weather_logo),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(428.dp)
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 64.sp,
                            color = Color.White
                        )
                    ) {
                        append("Weather\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = poppinsRegular,
                            fontWeight = FontWeight.Normal,
                            fontSize = 64.sp,
                            color = Color(0xFFDDB130)
                        )
                    ) {
                        append("ForeCasts")
                    }
                },
                textAlign = TextAlign.Center,
                lineHeight = 66.sp
            )

            Button(
                onClick = { /* next screen */ },
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Color(0xFFDDB130)
                    ),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .height(72.dp)
                    .width(304.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF362A84),
                    fontFamily = openSans
                )
            }
        }
    }
}