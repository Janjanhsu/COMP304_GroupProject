package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.R
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.Location
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation.Screen
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util.getDrawableIDByName
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util.getLocations

@Composable
fun LocationListScreen(category: String, navController: NavController) {
    val locations = getLocations(navController)
    val list = locations.getLocationsByCategory(category)
    LazyColumn {
        items(list) { location ->
            LocationCard(location, navController) {
                //navController.navigate(Screen.Yichen_activity.createRoute(string))
            }
        }
    }
}

@Composable
fun LocationCard(location: Location, navController: NavController, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(90.dp),
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(10.dp)
            .height(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        //.size(120.dp)
                        .clip(CircleShape)
                        //.background(Color.Red)
                ){
                    val filename = location.photo.substringBeforeLast(".")
                    Log.i("myApp", filename)
                    val id = getDrawableIDByName(filename, navController)
                    Image(
                        painter = painterResource(id = id),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }

            }

        }
        Column (modifier = Modifier
            .padding(18.dp)){
            Text(" ${location.name}", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}