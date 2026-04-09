package com.mdzeviatau.calculator.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdzeviatau.calculator.ui.viewmodel.CalculatorAction

@Composable
fun NumpadRow(
    modifier: Modifier = Modifier,
    t1: String,
    t2: String,
    t3: String,
    t4: String,
    textColor: Color,
    bgColor: Color,
    opColor: Color,
    fontSize: TextUnit = 25.sp,
    onAction: (CalculatorAction) -> Unit
) {
    val getAction: (String) -> CalculatorAction = { text ->
        when (text) {
            "AC" -> CalculatorAction.Clear
            "=" -> CalculatorAction.Calculate
            "+/-" -> CalculatorAction.ToggleSign
            "( )" -> CalculatorAction.Parentheses
            "%" -> CalculatorAction.Percent
            else -> CalculatorAction.Input(text)
        }
    }
    Row(modifier = modifier.fillMaxWidth()) {
        val btnModifier = Modifier
            .weight(1f)
            .padding(4.dp)
            .fillMaxHeight()

        NumButton(
            text = t1,
            textColor = textColor,
            containerColor = bgColor,
            modifier = btnModifier,
            fontSize = fontSize,
            onClick = { onAction(getAction(t1)) })
        NumButton(
            text = t2,
            textColor = textColor,
            containerColor = bgColor,
            modifier = btnModifier,
            fontSize = fontSize,
            onClick = { onAction(getAction(t2)) })
        NumButton(
            text = t3,
            textColor = textColor,
            containerColor = bgColor,
            modifier = btnModifier,
            fontSize = fontSize,
            onClick = { onAction(getAction(t3)) })
        NumButton(
            text = t4,
            textColor = Color.White,
            containerColor = opColor,
            modifier = btnModifier,
            fontSize = fontSize,
            onClick = { onAction(getAction(t4)) })
    }
}