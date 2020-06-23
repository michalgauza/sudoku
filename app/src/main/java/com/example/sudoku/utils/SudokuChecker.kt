package com.example.sudoku.utils

import com.example.sudoku.models.Cell
import com.example.sudoku.models.SudokuBoard
import com.example.sudoku.views.*
import com.example.sudoku.extensions.getCellsListWithSameNumber
import com.example.sudoku.extensions.setCellsRepeated

class SudokuChecker {

    fun checkBoard(sudokuBoard: SudokuBoard?): Boolean {
        sudokuBoard?.cellsList?.let { cellsList ->
            setAllCellsToNotRepeated(cellsList)
            checkRows(cellsList)
            checkColumns(cellsList)
            checkRectangles(cellsList)
            return checkIfWin(cellsList)
        }
        return false
    }

    private fun checkIfWin(cellsList: List<Cell>) =
        cellsList.none { cell -> cell.isRepeated || cell.number == EMPTY_CELL_NUMBER }

    private fun setAllCellsToNotRepeated(list: List<Cell>) {
        list.filter { cell -> cell.isRepeated }
            .forEach { cell -> cell.isRepeated = false }
    }

    private fun checkRows(allCellsList: List<Cell>) {
        allCellsList.let { cellsList ->
            for (columnOffset in 0 until CELLS_IN_BOARD step CELLS_IN_ROW) {
                checkRow(cellsList.subList(columnOffset, CELLS_IN_ROW + columnOffset))
            }
        }
    }

    private fun checkRow(row: List<Cell>) {
        row.getCellsListWithSameNumber().setCellsRepeated()
    }

    private fun checkColumns(allCellsList: List<Cell>) {
        for (columnIndex in 0 until COLUMNS_IN_BOARD) {
            val subList = mutableListOf<Cell>()
            for (rowOffset in 0 until CELLS_IN_BOARD step CELLS_IN_ROW) {
                subList.add(allCellsList[rowOffset + columnIndex])
            }
            checkColumn(subList)
        }
    }

    private fun checkColumn(column: List<Cell>) {
        column.getCellsListWithSameNumber().setCellsRepeated()
    }

    private fun checkRectangles(allCellsList: List<Cell>) {
        for (rectangleRowOffset in 0 until CELLS_IN_BOARD step CELLS_IN_LINE * LINES_IN_RECT) {
            for (rowOffset in 0 until CELLS_IN_RECT step LINES_IN_RECT) {
                val cellsInRectangleList = mutableListOf<Cell>()
                for (colOffset in 0 until CELLS_IN_LINE * LINES_IN_RECT step CELLS_IN_RECT) {
                    cellsInRectangleList.addAll(
                        allCellsList.subList(
                            colOffset + rowOffset + rectangleRowOffset,
                            LINES_IN_RECT + colOffset + rowOffset + rectangleRowOffset
                        )
                    )
                }
                checkRectangle(cellsInRectangleList)
            }
        }
    }

    private fun checkRectangle(rectangle: List<Cell>) {
        rectangle.getCellsListWithSameNumber().toList().setCellsRepeated()
    }
}