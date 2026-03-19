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
                val currentExpression = _state.value.expression
                val symbols = listOf("+", "-", "*", "/", ".", "%")

                if (currentExpression.isEmpty() && inputVal in listOf("+", "*", "/", ".", "-")) {
                    return
                }

                if (currentExpression.isNotEmpty()) {
                    val lastChar = currentExpression.last().toString()

                    if (inputVal in symbols && lastChar in symbols) {
                        _state.value =
                            _state.value.copy(expression = currentExpression.dropLast(1) + inputVal)
                        return
                    }
                }

                _state.value = _state.value.copy(
                    expression = _state.value.expression + inputVal
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

            is CalculatorAction.ToggleSign -> {
                val currentExpression = _state.value.expression

                if (currentExpression.isEmpty()) {
                    return
                }

                if (currentExpression.startsWith("-")) {
                    _state.value = _state.value.copy(
                        expression = currentExpression.substring(1)
                    )
                } else {
                    _state.value = _state.value.copy(
                        expression = "-$currentExpression"
                    )
                }
            }
        }
    }
}