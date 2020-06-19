package com.example.sudoku

data class Cell(val column: Int, val row: Int, var number: Int = 0, val editable: Boolean) {
    fun valueToString(): String = if (number == EMPTY_CELL_NUMBER) "" else number.toString()
}