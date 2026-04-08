package com.mdzeviatau.calculator.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SimpleCalc : Screen("simple_calc")
    object AdvancedCalc : Screen("advanced_calc")
    object About : Screen("about")
}