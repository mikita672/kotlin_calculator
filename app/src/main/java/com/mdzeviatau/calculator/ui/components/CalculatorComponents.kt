package com.mdzeviatau.calculator.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumpadRow(
    t1: String,
    t2: String,
    t3: String,
    t4: String,
    textColor: Color,
    bgColor: Color,
    opColor: Color,
    modifier: Modifier = Modifier
) {
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
            onClick = {})
        NumButton(
            text = t2,
            textColor = textColor,
            containerColor = bgColor,
            modifier = btnModifier,
            onClick = {})
        NumButton(
            text = t3,
            textColor = textColor,
            containerColor = bgColor,
            modifier = btnModifier,
            onClick = {})
        NumButton(
            text = t4,
            textColor = Color.White,
            containerColor = opColor,
            modifier = btnModifier,
            onClick = {})
    }
}

@Composable
fun NumButton(
    text: String,
    textColor: Color,
    containerColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor, contentColor = textColor
        )
    ) {
        Text(text, fontSize = 30.sp)
    }
}