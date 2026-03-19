package com.mdzeviatau.calculator.ui.viewmodel

data class CalculatorState(
    val expression: String = "",
    val isResult: Boolean = false
)

sealed interface CalculatorAction {
    data class Input(val value: String) : CalculatorAction
    object Clear : CalculatorAction
    object Calculate : CalculatorAction
    object ToggleSign : CalculatorAction
    object Parentheses : CalculatorAction
}