package com.example.yichen.yichen_kwokwing_comp304sec001_lab04

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.ui.theme.Yichen_kwokwing_COMP304Sec001_Lab04Theme

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
    fun LocationScreen(category: String?) {
        Box(Modifier.safeDrawingPadding().fillMaxWidth().fillMaxHeight().paint(
            painterResource(id = R.drawable.bg),
            contentScale = ContentScale.FillBounds)
        ) {
            Text(
                text = "Totonto Landmark Locator: $category",
                fontSize = 30.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)

            )
        }
    }
}
