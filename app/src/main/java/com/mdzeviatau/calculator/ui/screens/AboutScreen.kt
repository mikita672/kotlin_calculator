package com.mdzeviatau.calculator.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Calculator App\n Author: Mikita Dzeviatau 253805\nAndroid calculator app with simple and advanced modes created using kotlin and Jetpack Compose",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}