package com.mdzeviatau.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdzeviatau.calculator.ui.theme.CalculatorTheme

val NumbersColor = Color(149, 139, 255, 255)
val SecondaryOperationsColor = Color(39, 32, 94, 255)
val PrimaryOperationsColor = Color(88, 75, 236, 255)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .height(1000.dp)
                    )
                }
            }
        }
    }
}

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
        NumpadRow("AC", "()", "%", "/", Color.White, SecondaryOperationsColor, PrimaryOperationsColor)
        NumpadRow("1", "2", "3", "*", Color.White, NumbersColor, PrimaryOperationsColor)
        NumpadRow("4", "5", "6", "-", Color.White, NumbersColor, PrimaryOperationsColor)
        NumpadRow("7", "8", "9", "+", Color.White, NumbersColor, PrimaryOperationsColor)
        NumpadRow("+/-", "0", ",", "=", Color.White, NumbersColor, PrimaryOperationsColor)
    }
}

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

@Preview(showBackground = true)
@Composable
fun NumpadPreview() {
    CalculatorTheme {
        MainView()
    }
}
