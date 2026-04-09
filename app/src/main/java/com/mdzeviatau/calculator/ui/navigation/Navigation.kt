package com.mdzeviatau.calculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdzeviatau.calculator.ui.screens.AboutScreen
import com.mdzeviatau.calculator.ui.screens.AdvancedCalculatorScreen
import com.mdzeviatau.calculator.ui.screens.SimpleCalculatorScreen
import com.mdzeviatau.calculator.ui.screens.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Screen.SimpleCalc.route) {
            SimpleCalculatorScreen()
        }

        composable(Screen.AdvancedCalc.route) {
            AdvancedCalculatorScreen()
        }

        composable(Screen.About.route) {
            AboutScreen()
        }
    }
}