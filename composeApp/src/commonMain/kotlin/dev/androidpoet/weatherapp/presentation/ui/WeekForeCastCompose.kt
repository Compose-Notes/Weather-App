package dev.androidpoet.weatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.androidpoet.weatherapp.domain.model.DailyWeatherRowModel
import dev.androidpoet.weatherapp.domain.viewmodel.WeatherForeCastViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import weather_app.composeapp.generated.resources.Res
import weather_app.composeapp.generated.resources.icon_left
import weather_app.composeapp.generated.resources.icon_right
import weather_app.composeapp.generated.resources.opensans_bold
import weather_app.composeapp.generated.resources.poppins_bold
import weather_app.composeapp.generated.resources.poppins_regular
import kotlin.math.max
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun DayForeCastCompose(modifier: Modifier,model: DailyWeatherRowModel,onClick: () -> Unit){
    val poppinsRegular = FontFamily(
        Font(Res.font.poppins_regular, weight = FontWeight.Normal)
    )
    Card (
       modifier = modifier ,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(40.dp,40.dp,40.dp,40.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(
            brush = Brush.verticalGradient(colors = model.gradient)
        )) {


            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top=10.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)){

// Fixed version of your code
                Text(
                    text = model.temp,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Transparent),
                    style = TextStyle(
                        fontFamily = poppinsRegular, // Fixed: Wrap in FontFamily
                        color = Color.White
                    )
                )

                Image(
                    painter = painterResource(model.img),
                    contentDescription = null, // Fixed: Added parameter name
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.Transparent)
                )

                Text(
                    text = model.day,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Transparent),
                    style = TextStyle( // Fixed: Use style instead of fontStyle
                        fontFamily = poppinsRegular, // Fixed: Wrap in FontFamily and use style
                        color = Color.White
                    )
                )            }

        }
    }
}


@Composable
fun DayForeCastRow(modifier: Modifier , viewModel: WeatherForeCastViewModel){
    Column (modifier=modifier.fillMaxWidth().padding(15.dp)){

        val uiState = viewModel.sevenDayForeCast.value
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        val openSans = FontFamily(
            Font(Res.font.opensans_bold, weight = FontWeight.Bold)
        )



        Text(text="7-Days Forecasts",modifier= Modifier.padding(start=28.dp,top=16.dp), style = TextStyle(fontFamily = openSans,color=Color.White))



        Spacer(modifier=Modifier.size(10.dp))

        Row (verticalAlignment =Alignment.CenterVertically, modifier = modifier.fillMaxWidth()){
            // have used animate scroll to avoid flickering for button scrolling
            Image(painter = painterResource(Res.drawable.icon_left),null,modifier = Modifier.size(24.dp).clickable(indication = null, interactionSource = null,onClick = {
                scope.launch {
                    listState.animateScrollToItem(max(0, listState.firstVisibleItemIndex-1))
                }
            }))

            LazyRow (state = listState,modifier = Modifier.padding(2.dp).weight(1f)){
                items(uiState.sevenDayForeCast,key = {it.dt}){
                    Spacer(modifier.size(2.dp))
                    DayForeCastCompose(Modifier,it){
                    }
                    Spacer(modifier.size(2.dp))
                }
            }

            Image(painterResource(Res.drawable.icon_right),null,modifier = Modifier.size(24.dp).clickable(indication = null, interactionSource = null, onClick = {
                scope.launch {
                    listState.animateScrollToItem(listState.firstVisibleItemIndex+1)
                }
            }))
        }
    }
}


@Preview
@Composable
fun DayForeCastRowPreview(){
    val viewModel = WeatherForeCastViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        DayForeCastRow(Modifier, viewModel)
    }
}





