package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view

import android.content.Context

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.Location
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.R
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.readCsv
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation.Screen
import kotlinx.coroutines.InternalCoroutinesApi
import java.io.InputStream


@Composable
@OptIn(InternalCoroutinesApi::class)
fun HomeScreen (navController: NavController) {
    val context: Context = navController.context
    val inputStream: InputStream = context.getResources().openRawResource(R.raw.attractions)

    val locations = readCsv(inputStream)
    val categories = locations.getCategory()
    //locations.forEach { Log.i("myApp", it.toString()) }

    Box(Modifier.safeDrawingPadding().fillMaxWidth().fillMaxHeight().paint(
        painterResource(id = R.drawable.bg),
        contentScale = ContentScale.FillBounds)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Totonto Landmark Locator",
                fontSize = 30.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            LazyColumn {
                items(categories) { string ->
                    CategoryCard(string, navController) {
                        navController.navigate(Screen.Yichen_activity.createRoute(string))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(string: String, navController: NavController, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Text(" ${string}", fontSize = 50.sp, fontWeight = FontWeight.Bold)
            }
        }

    }
}
