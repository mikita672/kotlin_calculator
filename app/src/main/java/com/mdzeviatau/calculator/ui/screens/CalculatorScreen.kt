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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mdzeviatau.calculator.ui.components.NumpadRow
import com.mdzeviatau.calculator.ui.theme.CalculatorTheme
import com.mdzeviatau.calculator.ui.theme.Primary
import com.mdzeviatau.calculator.ui.theme.Secondary
import com.mdzeviatau.calculator.ui.theme.Tertiary
import com.mdzeviatau.calculator.ui.viewmodel.CalculatorAction
import com.mdzeviatau.calculator.ui.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier, viewModel: CalculatorViewModel = viewModel<CalculatorViewModel>()
) {
    val state by viewModel.state.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(modifier = modifier) {
            Display(modifier = Modifier.weight(1f), text = state.expression)
            Numpad(modifier = Modifier.weight(1.5f), onAction = viewModel::onAction)
        }
    } else {
        Column(modifier = modifier) {
            Display(modifier = Modifier.weight(1.4f), text = state.expression)
            Numpad(modifier = Modifier.weight(2f), onAction = viewModel::onAction)
        }
    }
}

@Composable
fun Display(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Text(text = text.ifEmpty { "0" }, fontSize = 50.sp)
    }
}

@Composable
fun Numpad(modifier: Modifier = Modifier, onAction: (CalculatorAction) -> Unit) {
    Column(modifier = modifier) {
        val rowModifier = Modifier.weight(1f)

        NumpadRow(
            rowModifier,
            "AC",
            "( )",
            "%",
            "/",
            Color.White,
            Secondary,
            Tertiary,
            onAction = onAction
        )
        NumpadRow(
            rowModifier, "1", "2", "3", "*", Color.White, Primary, Tertiary, onAction = onAction
        )
        NumpadRow(
            rowModifier, "4", "5", "6", "-", Color.White, Primary, Tertiary, onAction = onAction
        )
        NumpadRow(
            rowModifier, "7", "8", "9", "+", Color.White, Primary, Tertiary, onAction = onAction
        )
        NumpadRow(
            rowModifier, "+/-", "0", ",", "=", Color.White, Primary, Tertiary, onAction = onAction
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumpadPreview() {
    CalculatorTheme {
        CalculatorScreen(modifier = Modifier.fillMaxSize())
    }
}
