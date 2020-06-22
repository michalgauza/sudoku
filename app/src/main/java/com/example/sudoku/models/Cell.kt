package com.example.sudoku.models

import com.example.sudoku.EMPTY_CELL_NUMBER

data class Cell(val column: Int, val row: Int, var number: Int, val editable: Boolean, var isRepeated: Boolean = false) {

    fun valueToString(): String = if (number == EMPTY_CELL_NUMBER) "" else number.toString()
}