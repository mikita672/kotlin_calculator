package com.mdzeviatau.calculator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdzeviatau.calculator.ui.navigation.Screen
import com.mdzeviatau.calculator.ui.theme.Secondary

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome Screen", fontSize = 40.sp, modifier = Modifier.padding(bottom = 32.dp))

        Button(
            onClick = { navController.navigate(Screen.SimpleCalc.route) },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Secondary)
        ) {
            Text("Simple Mode", fontSize = 18.sp)
        }

        Button(
            onClick = { navController.navigate(Screen.AdvancedCalc.route) },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Secondary
            )
        ) {
            Text("Advanced Mode", fontSize = 18.sp)
        }

        Button(
            onClick = { navController.navigate(Screen.About.route) },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Secondary)
        ) {
            Text("About", fontSize = 18.sp)
        }
    }
}