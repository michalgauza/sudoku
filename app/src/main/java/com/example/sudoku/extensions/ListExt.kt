package com.example.sudoku.extensions

import com.example.sudoku.models.Cell

fun List<Cell>.getCellByRowAndColumn(row: Int, column: Int) =
    firstOrNull { cell -> cell.row == row && cell.column == column }

fun List<Cell>.getCellsListWithSameNumber(): List<Cell> {
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
    return duplicates.toList()
}

fun List<Cell>.setCellsRepeated() {
    this.forEach { duplicatedCell ->
        duplicatedCell.isRepeated = true
    }
}

fun List<Int>.toCellsList(cellsInRow: Int, cellsInColumn: Int, emptyCellNumber: Int): List<Cell> {
    val list = mutableListOf<Cell>()
    var numberIndex = 0
    for (row in 0 until cellsInRow) {
        for (col in 0 until cellsInColumn) {
            list.add(
                Cell(
                    col,
                    row,
                    this[numberIndex],
                    this[numberIndex] == emptyCellNumber
                )
            )
            numberIndex++
        }
    }
    return list
}

