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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdzeviatau.calculator.ui.theme.CalculatorTheme

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
    Column {
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
        NumpadRow("AC", "()", "%", "/", Modifier.fillMaxWidth())
        NumpadRow("1", "2", "3","*", Modifier.fillMaxWidth())
        NumpadRow("4", "5", "6", "-", Modifier.fillMaxWidth())
        NumpadRow("7", "8", "9", "+", Modifier.fillMaxWidth())
        NumpadRow("+/-", "0", ",", "=", Modifier.fillMaxWidth())
    }
}

@Composable
fun NumpadRow(textButton1: String, textButton2: String, textButton3: String, textButton4: String, modifier: Modifier = Modifier){
    Row(modifier = modifier) {
        NumButton({}, textButton1, Modifier
            .weight(1f)
            .padding(3.dp)
            .height(100.dp))
        NumButton({}, textButton2, Modifier
            .weight(1f)
            .padding(3.dp)
            .height(100.dp)
        )
        NumButton({}, textButton3, Modifier
            .weight(1f)
            .padding(3.dp)
            .height(100.dp)
        )
        NumButton({}, textButton4, Modifier
            .weight(1f)
            .padding(3.dp)
            .height(100.dp))
    }
}

@Composable
fun NumButton(onClick:() -> Unit, text: String, modifier: Modifier = Modifier){
    Button(onClick = {onClick()}, modifier=modifier){
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
