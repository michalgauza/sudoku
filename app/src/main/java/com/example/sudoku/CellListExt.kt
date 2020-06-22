package com.example.sudoku

import com.example.sudoku.models.Cell

fun List<Cell>.getCellByRowAndColumn(row: Int, column: Int) =
    firstOrNull { cell -> cell.row == row && cell.column == column }

fun List<Cell>.getDuplicatedNumbers(): MutableSet<Cell> {
    val duplicates = mutableSetOf<Cell>()
    var previous: Cell? = null
    this.filter { cell -> cell.number != 0 }.sortedBy { it.number }.also { sortedList ->
        sortedList.forEach { cell ->
            if (cell.number == previous?.number) {
                duplicates.add(cell)
                previous?.let { duplicates.add(it) }
            }
            previous = cell
        }
    }
    return duplicates
}

fun List<Cell>.setCellsRepeated() {
    this.forEach { duplicatedCell ->
        if (duplicatedCell.editable)
            duplicatedCell.isRepeated = true
    }
}