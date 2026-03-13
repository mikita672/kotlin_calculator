package com.mdzeviatau.calculator.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
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
    t1: String, t2: String, t3: String, t4: String,
    textColor: Color, bgColor: Color, opColor: Color,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier) {
        val btnModifier = Modifier.weight(1f).padding(4.dp).height(90.dp)

        NumButton({}, t1, textColor, bgColor, btnModifier)
        NumButton({}, t2, textColor, bgColor, btnModifier)
        NumButton({}, t3, textColor, bgColor, btnModifier)
        NumButton({}, t4, Color.White, opColor, btnModifier)
    }
}

@Composable
fun NumButton(onClick:() -> Unit, text: String, textColor: Color, containerColor: Color, modifier: Modifier = Modifier){
    Button(onClick = {onClick()}, modifier=modifier, colors= ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = textColor)){
        Text(text, fontSize = 30.sp)
    }
}