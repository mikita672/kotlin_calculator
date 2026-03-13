package com.mdzeviatau.calculator.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdzeviatau.calculator.ui.theme.Primary
import com.mdzeviatau.calculator.ui.components.NumpadRow
import com.mdzeviatau.calculator.ui.theme.Tertiary
import com.mdzeviatau.calculator.ui.theme.Secondary

@Composable
fun MainView(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Display(Modifier.padding(30.dp).height(250.dp))
        Numpad(Modifier.fillMaxSize())
    }
}

@Composable
fun Display(modifier: Modifier = Modifier){
    Box(modifier = modifier) {
        Text("Hello", fontSize = 50.sp)
    }
}

@Composable
fun Numpad(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        NumpadRow("AC", "( )", "%", "/", Color.White, Secondary, Tertiary)
        NumpadRow("1", "2", "3", "*", Color.White, Primary, Tertiary)
        NumpadRow("4", "5", "6", "-", Color.White, Primary, Tertiary)
        NumpadRow("7", "8", "9", "+", Color.White, Primary, Tertiary)
        NumpadRow("+/-", "0", ",", "=", Color.White, Primary, Tertiary)
    }
}
