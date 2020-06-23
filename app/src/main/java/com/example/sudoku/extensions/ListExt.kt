package com.example.sudoku.extensions

import com.example.sudoku.models.Cell
import com.example.sudoku.views.CELLS_IN_LINE
import com.example.sudoku.utils.EMPTY_CELL_NUMBER

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

fun List<Int>.toCellsList(): List<Cell> = List(this.size) { i ->
    Cell(
        i % CELLS_IN_LINE,
        i / CELLS_IN_LINE,
        this[i],
        this[i] == EMPTY_CELL_NUMBER
    )
}