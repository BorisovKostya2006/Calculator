package com.example.calculator

import androidx.compose.runtime.mutableStateOf


class CalculatorViewModel {
    var state = mutableStateOf(
        Display(
            calculations = "45x8",
            result = "320"
        )
    )
    fun  processInputUser(name : String){
        if(name == "AC"){
            state.value = Display(
                calculations = "",
                result = ""
            )
        }

    }
}

data class Display(
    var calculations : String,
    var result : String
)