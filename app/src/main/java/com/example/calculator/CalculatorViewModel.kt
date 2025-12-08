package com.example.calculator

import android.util.Log
import androidx.annotation.Nullable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.mariuszgromada.math.mxparser.Expression
import kotlin.random.Random


class CalculatorViewModel : ViewModel() {
    private var _state : MutableStateFlow<CalculatorState> = MutableStateFlow(
        CalculatorState.Initial
    )
    private var expression = ""
    var state = _state.asStateFlow()
    fun processCommand (command: CalculatorCommand) {
        Log.d("processCommand", command.toString())
        when (command) {
            CalculatorCommand.Clear -> {
                expression = ""
                _state.value =  CalculatorState.Initial
            }
            CalculatorCommand.Evaluate -> {
                val result = evaluate()
                if (result == "NaN"){
                    _state.value = CalculatorState.Error(
                        expression = expression
                    )
                }else{
                    _state.value = CalculatorState.Success(
                        result = result
                    )
                }


            }
            is CalculatorCommand.Input -> {
                val symbol = if (command.symbol != Symbol.PARENTHESIS) {
                     command.symbol.value
                }else{
                     getCorrectParenthesis()
                }
                val result  = evaluate()
                expression += symbol
                _state.value = CalculatorState.Input(
                    expression = expression,
                    result = if(result == "NaN") {
                        ""
                    }else{
                        result
                    }
                )
            }
        }
    }

    private fun evaluate() : String{
        val expressionStr = expression.replace('x', '*')
            .replace(',','.')
        val answer = Expression(expressionStr).calculate().toString()
        return answer
    }
//    fun  processInputUser(name : String){
//        if(name == "AC"){
//            _state.value = CalculatorState.Initial
//        }
//
//    }
    private fun getCorrectParenthesis (): String {
        val openCount: Int = expression.count { it == '(' }
        val closeCount: Int = expression.count {it == ')' }
        return when {
            expression.isEmpty() -> "("
            !expression.last().isDigit() && expression.last() != '(' && expression. last() != 'π'
                -> "("
            openCount > closeCount -> ")"
            else -> "("
        }
    }
}

sealed interface CalculatorState {
    data object Initial: CalculatorState

    data class Input(
        val expression: String,
        val result: String
    ) : CalculatorState

    data class Success (val result: String): CalculatorState

    data class Error(val expression: String): CalculatorState
}

sealed interface CalculatorCommand {
    data object Clear: CalculatorCommand
    data object Evaluate: CalculatorCommand
    data class Input(val symbol: Symbol): CalculatorCommand
}
enum class Symbol(val value : String) {
    DIGIT_0("0"),
    DIGIT_1("1"),
    DIGIT_2("2"),
    DIGIT_3("3"),
    DIGIT_4("4"),
    DIGIT_5("5"),
    DIGIT_6("6"),
    DIGIT_7("7"),
    DIGIT_8("8"),
    DIGIT_9("9"),
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("x"),
    DIVIDE("÷"),
    PERCENT("%"),
    POWER("^"),
    FACTORIAL("!"),
    SQRT("√"),
    PI("π"),
    DOT(","),
    PARENTHESIS("()")
}
data class Display(
    var calculations : String,
    var result : String
)