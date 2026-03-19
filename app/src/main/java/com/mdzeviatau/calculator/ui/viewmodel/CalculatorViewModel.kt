package com.mdzeviatau.calculator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    fun onAction(action: CalculatorAction) {
        if (_state.value.expression == "Error" && action !is CalculatorAction.Clear) {
            return
        }

        when (action) {
            is CalculatorAction.Clear -> {
                _state.value = CalculatorState(expression = "")
            }

            is CalculatorAction.Input -> {
                val inputVal = if (action.value == ",") "." else action.value
                val symbols = listOf("+", "-", "*", "/", ".", "%")
                val operators = listOf("+", "-", "*", "/")

                var currentExpression = _state.value.expression

                if (_state.value.isResult) {
                    if (inputVal !in operators) {
                        currentExpression = ""
                    }
                }

                if (currentExpression.isEmpty() && inputVal in listOf("+", "*", "/", ".", "-")) {
                    return
                }

                if (currentExpression.isNotEmpty()) {
                    val lastChar = currentExpression.last().toString()

                    if (inputVal in symbols && lastChar in symbols) {
                        _state.value =
                            _state.value.copy(
                                expression = currentExpression.dropLast(1) + inputVal,
                                isResult = false
                            )
                        return
                    }
                }

                _state.value = _state.value.copy(
                    expression = currentExpression + inputVal, isResult = false
                )
            }

            is CalculatorAction.Calculate -> {
                Log.d("Calculator", "Calculate button pressed")
                try {
                    val expression = ExpressionBuilder(_state.value.expression).build()
                    val result = expression.evaluate()
                    val resultStr = if (result == result.toLong().toDouble()) {
                        result.toLong().toString()
                    } else {
                        result.toString()
                    }

                    _state.value = _state.value.copy(expression = resultStr, isResult = true)
                } catch (_: Exception) {
                    _state.value = _state.value.copy(expression = "Error", isResult = true)
                }
            }

            is CalculatorAction.ToggleSign -> {
                Log.d("Toggle Sign", "Parentheses button pressed")
                val currentExpression = _state.value.expression

                if (currentExpression.isEmpty()) {
                    _state.value = _state.value.copy(expression = "-", isResult = false)
                    return
                }

                if (currentExpression.startsWith("-")) {
                    _state.value = _state.value.copy(
                        expression = currentExpression.substring(1), isResult = false
                    )
                } else {
                    _state.value = _state.value.copy(
                        expression = "-$currentExpression", isResult = false
                    )
                }
            }

            is CalculatorAction.Parentheses -> {
                Log.d("Calculator", "Parentheses button pressed")
                val currentExpression = _state.value.expression

                val openCount = currentExpression.count { it == '(' }
                val closeCount = currentExpression.count { it == ')' }
                val lastChar = currentExpression.lastOrNull()

                val toAdd =
                    if (openCount > closeCount && lastChar != null && (lastChar.isDigit() || lastChar == ')')) {
                        ")"
                    } else {
                        if (lastChar != null && (lastChar.isDigit() || lastChar == ')')) {
                            "*("
                        } else {
                            "("
                        }
                    }

                _state.value = _state.value.copy(
                    expression = currentExpression + toAdd, isResult = false
                )
            }
        }
    }
}