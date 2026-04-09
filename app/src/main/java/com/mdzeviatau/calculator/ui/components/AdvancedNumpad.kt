package com.mdzeviatau.calculator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.mdzeviatau.calculator.ui.theme.Primary
import com.mdzeviatau.calculator.ui.theme.Tertiary
import com.mdzeviatau.calculator.ui.viewmodel.CalculatorAction

@Composable
fun AdvancedNumpad(modifier: Modifier = Modifier, onAction: (CalculatorAction) -> Unit) {
    Column(modifier = modifier) {
        val rowModifier = Modifier.weight(1f)

        NumpadRow(
            rowModifier,
            "sin",
            "cos",
            "tan",
            "sqrt",
            Color.White,
            Primary,
            Tertiary,
            fontSize = 22.sp,
            onAction = onAction
        )
        NumpadRow(
            rowModifier,
            "ln",
            "log",
            "x^2",
            "x^y",
            Color.White,
            Primary,
            Tertiary,
            fontSize = 22.sp,
            onAction = onAction
        )
    }
}