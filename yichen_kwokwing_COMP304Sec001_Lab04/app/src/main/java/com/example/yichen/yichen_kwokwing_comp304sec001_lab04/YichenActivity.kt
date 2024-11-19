package com.example.yichen.yichen_kwokwing_comp304sec001_lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation.Screen

import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.ui.theme.Yichen_kwokwing_COMP304Sec001_Lab04Theme
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view.LocationListScreen

class YichenActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yichen_kwokwing_COMP304Sec001_Lab04Theme {

            }

        }
    }
    @Composable
    fun LocationScreen(category: String?, navController: NavController) {
        Box(Modifier.safeDrawingPadding().fillMaxWidth().fillMaxHeight().paint(
            painterResource(id = R.drawable.bg),
            contentScale = ContentScale.FillBounds)
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Image(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(size = 80.dp)
                    //.clickable { navController.navigate(Screen.Home) }
                    .clickable { navController.popBackStack() }
                )

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Totonto Landmark Locator: $category",
                        fontSize = 30.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)

                    )
                    if (category != null) {
                        LocationListScreen(category, navController)
                    }
                }
            }
        }
    }
}
