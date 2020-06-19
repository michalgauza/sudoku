package com.example.sudoku

fun List<Cell>.getCellByRowAndColumn(row: Int, column: Int) =
    firstOrNull { cell -> cell.row == row && cell.column == column }
