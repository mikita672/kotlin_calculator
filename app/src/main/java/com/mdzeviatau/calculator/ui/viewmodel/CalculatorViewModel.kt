package com.mdzeviatau.calculator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    private var lastRemoveTime = 0L

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
                    var textToCalculate = _state.value.expression
                    val openCount = textToCalculate.count { it == '(' }
                    val closeCount = textToCalculate.count { it == ')' }

                    if (openCount > closeCount) {
                        textToCalculate += ")".repeat(openCount - closeCount)
                    }

                    val expression = ExpressionBuilder(textToCalculate).build()
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
                var currentExpression = _state.value.expression

                if (_state.value.isResult) {
                    currentExpression = if (currentExpression.startsWith("-")) {
                        currentExpression.substring(1)
                    } else {
                        "-$currentExpression"
                    }
                    _state.value =
                        _state.value.copy(expression = currentExpression, isResult = false)
                    return
                }

                var splitIndex = currentExpression.length
                while (splitIndex > 0 && (currentExpression[splitIndex - 1].isDigit() || currentExpression[splitIndex - 1] == '.')) {
                    splitIndex--
                }

                val prefix = currentExpression.substring(0, splitIndex)
                val number = currentExpression.substring(splitIndex)

                val newExpression = when {
                    prefix.endsWith("(-") -> prefix.dropLast(2) + number
                    prefix == "-" -> number
                    prefix.isEmpty() -> "-$number"
                    else -> "$prefix(-$number"
                }

                _state.value = _state.value.copy(expression = newExpression, isResult = false)
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

            is CalculatorAction.RemoveOneSymbol -> {
                Log.d("Calculator", "Remove One Symbol Button pressed")
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastRemoveTime < 500) {
                    _state.value = CalculatorState(expression = "")
                } else {
                    val currentExpression = _state.value.expression
                    if (currentExpression.isNotEmpty()) {
                        _state.value = _state.value.copy(
                            expression = currentExpression.dropLast(1),
                            isResult = false
                        )
                    }
                }
                lastRemoveTime = currentTime
            }
        }
    }
}