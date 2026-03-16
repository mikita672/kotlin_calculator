package com.mdzeviatau.calculator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mdzeviatau.calculator.ui.theme.Primary
import com.mdzeviatau.calculator.ui.theme.Secondary
import com.mdzeviatau.calculator.ui.theme.Tertiary
import com.mdzeviatau.calculator.ui.viewmodel.CalculatorAction

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