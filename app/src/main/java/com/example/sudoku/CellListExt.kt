package com.example.sudoku

fun List<Cell>.getCellByRowAndColumn(row: Int, column: Int) =
    firstOrNull { cell -> cell.row == row && cell.column == column }

fun List<Cell>.getDuplicate(): List<Cell> {
    val duplicates = mutableListOf<Cell>()
    var previous: Cell? = null
    this.sortedBy { it.number }.also { sortedList ->
        sortedList.forEach { cell ->
            if (cell.number == previous?.number) {
                duplicates.add(cell)
            }
            previous = cell
        }
    }
    return duplicates
}