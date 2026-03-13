package com.mdzeviatau.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdzeviatau.calculator.ui.theme.Primary
import com.mdzeviatau.calculator.ui.components.NumpadRow
import com.mdzeviatau.calculator.ui.theme.CalculatorTheme
import com.mdzeviatau.calculator.ui.theme.Tertiary
import com.mdzeviatau.calculator.ui.theme.Secondary

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(modifier = modifier) {
            Display(modifier = Modifier.weight(1f))
            Numpad(modifier = Modifier.weight(1.5f))
        }
    } else {
        Column(modifier = modifier) {
            Display(modifier = Modifier.weight(1.4f))
            Numpad(modifier = Modifier.weight(2f))
        }
    }
}

@Composable
fun Display(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Text("Hello", fontSize = 50.sp)
    }
}

@Composable
fun Numpad(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        val rowModifier = Modifier.weight(1f)

        NumpadRow("AC", "( )", "%", "/", Color.White, Secondary, Tertiary, rowModifier)
        NumpadRow("1", "2", "3", "*", Color.White, Primary, Tertiary, rowModifier)
        NumpadRow("4", "5", "6", "-", Color.White, Primary, Tertiary, rowModifier)
        NumpadRow("7", "8", "9", "+", Color.White, Primary, Tertiary, rowModifier)
        NumpadRow("+/-", "0", ",", "=", Color.White, Primary, Tertiary, rowModifier)
    }
}

@Preview(showBackground = true)
@Composable
fun NumpadPreview() {
    CalculatorTheme {
        CalculatorScreen(modifier = Modifier.fillMaxSize())
    }
}
