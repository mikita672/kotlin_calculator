package com.mdzeviatau.calculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Clear -> {
                _state.value = CalculatorState(expression = "")
            }

            is CalculatorAction.Input -> {
                val inputVal = if (action.value == ",") "." else action.value
                val finalInput = if (inputVal == "+/-") "-" else inputVal

                _state.value = _state.value.copy(
                    expression = _state.value.expression + finalInput
                )
            }

            is CalculatorAction.Calculate -> {
                try {
                    val expression = ExpressionBuilder(_state.value.expression).build()
                    val result = expression.evaluate()
                    val resultStr = if (result == result.toLong().toDouble()) {
                        result.toLong().toString()
                    } else {
                        result.toString()
                    }

                    _state.value = _state.value.copy(expression = resultStr)
                } catch (_: Exception) {
                    _state.value = _state.value.copy(expression = "Error")
                }
            }
        }
    }
}