package com.mdzeviatau.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdzeviatau.calculator.ui.theme.CalculatorTheme
import com.mdzeviatau.calculator.ui.screens.MainView



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




@Preview(showBackground = true)
@Composable
fun NumpadPreview() {
    CalculatorTheme {
        MainView()
    }
}
