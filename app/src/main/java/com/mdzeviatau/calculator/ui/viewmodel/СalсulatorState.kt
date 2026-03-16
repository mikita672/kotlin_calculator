package com.mdzeviatau.calculator.ui.viewmodel

data class CalculatorState(
    val expression: String = ""
)

sealed interface CalculatorAction {
    data class Input(val value: String) : CalculatorAction
    object Clear : CalculatorAction
    object Calculate : CalculatorAction
}