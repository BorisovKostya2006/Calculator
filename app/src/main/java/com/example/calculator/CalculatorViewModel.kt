package com.example.calculator

import android.util.Log
import androidx.compose.runtime.mutableStateOf


class CalculatorViewModel {
    var state = mutableStateOf(
        Display(
            calculations = "45x8",
            result = "320"
        )
    )
    fun processCommand (command: CalculatorCommand) {
        Log.d("processCommand", command.toString())
        when (command) {
            CalculatorCommand.Clear -> Log.d("Clear", command.toString())
            CalculatorCommand.Evaluate -> Log.d("Evaluate", command.toString())
            is CalculatorCommand.Input -> Log.d("Input", command.toString())
        }
    }
    fun  processInputUser(name : String){
        if(name == "AC"){
            state.value = Display(
                calculations = "",
                result = ""
            )
        }

    }
}

sealed interface CalculatorCommand {
    data object Clear: CalculatorCommand
    data object Evaluate: CalculatorCommand
    data class Input(val symbol: Symbol): CalculatorCommand
}
enum class Symbol {
    DIGIT_0,
    DIGIT_1,
    DIGIT_2,
    DIGIT_3,
    DIGIT_4,
    DIGIT_5,
    DIGIT_6,
    DIGIT_7,
    DIGIT_8,
    DIGIT_9,
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    PERCENT,
    POWER,
    FACTORIAL,
    SQRT,
    PI,
    DOT,
    PARENTHESIS
}
data class Display(
    var calculations : String,
    var result : String
)