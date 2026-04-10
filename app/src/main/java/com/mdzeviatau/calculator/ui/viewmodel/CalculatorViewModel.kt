package com.mdzeviatau.calculator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.tan

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
                val symbols = listOf("+", "-", "*", "/", ".", "%", "^")
                val operators = listOf("+", "-", "*", "/", "^")

                var currentExpression = _state.value.expression

                if (_state.value.isResult) {
                    if (inputVal !in operators) {
                        currentExpression = ""
                    }
                }

                if (currentExpression.isEmpty()) {
                    if (inputVal in symbols) {
                        _state.value = _state.value.copy(
                            expression = "0$inputVal"
                        )
                        return
                    }
                }

                if (currentExpression.isNotEmpty()) {
                    val lastChar = currentExpression.last()

                    if (lastChar == ')') {
                        if (inputVal == ".") {
                            _state.value = _state.value.copy(
                                expression = "$currentExpression*0.", isResult = false
                            )
                            return
                        } else if (inputVal.first().isDigit()) {
                            _state.value = _state.value.copy(
                                expression = "$currentExpression*$inputVal", isResult = false
                            )
                            return
                        }
                    }

                    if (inputVal in symbols && lastChar.toString() in symbols) {
                        _state.value = _state.value.copy(
                            expression = currentExpression.dropLast(1) + inputVal, isResult = false
                        )
                        return
                    }

                    if (inputVal == "." && lastChar.toString() in operators) {
                        _state.value = _state.value.copy(
                            expression = currentExpression + "0.", isResult = false
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

                    val builder = ExpressionBuilder(textToCalculate)

                    val sinDeg = object : Function("sin", 1) {
                        override fun apply(vararg args: Double): Double {
                            val res = sin(Math.toRadians(args[0]))
                            val rounded = res.roundToInt().toDouble()
                            return if (abs(res - rounded) < 1e-10) rounded else res
                        }
                    }
                    val cosDeg = object : Function("cos", 1) {
                        override fun apply(vararg args: Double): Double {
                            val res = cos(Math.toRadians(args[0]))
                            val rounded = res.roundToInt().toDouble()
                            return if (abs(res - rounded) < 1e-10) rounded else res
                        }
                    }
                    val tanDeg = object : Function("tan", 1) {
                        override fun apply(vararg args: Double): Double {
                            val absoluteDeg = abs(args[0] % 180)
                            if (absoluteDeg == 90.0) throw ArithmeticException("Undefined")

                            if (abs(absoluteDeg - 90.0) < 1e-10) {
                                throw ArithmeticException("Undefined")
                            }

                            val res = tan(Math.toRadians(args[0]))
                            val rounded = res.roundToInt().toDouble()
                            return if (abs(res - rounded) < 1e-10) rounded else res
                        }
                    }

                    val lnFunc = object : Function("ln", 1) {
                        override fun apply(vararg args: Double): Double {
                            if (args[0] <= 0) throw ArithmeticException("Domain error")
                            return ln(args[0])
                        }
                    }

                    val logFunc = object : Function("log", 1) {
                        override fun apply(vararg args: Double): Double {
                            if (args[0] <= 0) throw ArithmeticException("Domain error")
                            return log10(args[0])
                        }
                    }

                    builder.functions(sinDeg, cosDeg, tanDeg, lnFunc, logFunc)
                    val expression = builder.build()
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
                        val functionsSymbols =
                            listOf("sin(", "cos(", "tan(", "sqrt(", "ln(", "log(")

                        val matchedChunk = functionsSymbols.find { currentExpression.endsWith(it) }

                        val newExpression = if (matchedChunk != null) {
                            currentExpression.dropLast(matchedChunk.length)
                        } else {
                            currentExpression.dropLast(1)
                        }

                        _state.value = _state.value.copy(
                            expression = newExpression, isResult = false
                        )
                    }
                }
                lastRemoveTime = currentTime
            }

            is CalculatorAction.Percent -> {
                Log.d("Calculator", "Percent button pressed")
                val currentExpression = _state.value.expression

                if (currentExpression.isEmpty()) return

                if (_state.value.isResult) {
                    val number = currentExpression.toDoubleOrNull() ?: return
                    val percentValue = number / 100.0

                    val formatted = if (percentValue == percentValue.toLong().toDouble()) {
                        percentValue.toLong().toString()
                    } else {
                        percentValue.toString()
                    }

                    _state.value = _state.value.copy(expression = formatted, isResult = true)
                    return
                }

                var splitIndex = currentExpression.length
                while (splitIndex > 0 && (currentExpression[splitIndex - 1].isDigit() || currentExpression[splitIndex - 1] == '.')) {
                    splitIndex--
                }

                if (splitIndex == currentExpression.length) return

                val prefix = currentExpression.substring(0, splitIndex)
                val numberStr = currentExpression.substring(splitIndex)

                val number = numberStr.toDoubleOrNull() ?: return
                val percentValue = number / 100.0

                val formattedPercent = if (percentValue == percentValue.toLong().toDouble()) {
                    percentValue.toLong().toString()
                } else {
                    percentValue.toString()
                }

                _state.value = _state.value.copy(
                    expression = prefix + formattedPercent, isResult = false
                )
            }

            is CalculatorAction.ScientificFunction -> {
                Log.d("Calculator", "Scientific button pressed")
                var currentExpression = _state.value.expression

                if (_state.value.isResult) {
                    currentExpression = ""
                }

                val lastChar = currentExpression.lastOrNull()

                val toAdd =
                    if (lastChar != null && (lastChar.isDigit() || lastChar == ')' || lastChar == '.')) {
                        "*${action.name}("
                    } else {
                        "${action.name}("
                    }

                _state.value =
                    _state.value.copy(expression = currentExpression + toAdd, isResult = false)
            }

            is CalculatorAction.Square -> {
                Log.d("Calculator", "Square button pressed")
                val currentExpression = _state.value.expression

                if (currentExpression.isEmpty()) return

                val lastChar = currentExpression.last()
                val operators = listOf("+", "-", "*", "/", "^")

                if (lastChar.isDigit() || lastChar == '.' || lastChar == ')') {
                    _state.value = _state.value.copy(
                        expression = "$currentExpression^2", isResult = false
                    )
                } else if (lastChar.toString() in operators) {
                    _state.value = _state.value.copy(
                        expression = currentExpression.dropLast(1) + "^2", isResult = false
                    )
                }
            }
        }
    }
}