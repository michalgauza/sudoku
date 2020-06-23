package com.example.sudoku.utils

import com.example.sudoku.models.Cell
import com.example.sudoku.models.SudokuBoard
import com.example.sudoku.views.*
import com.example.sudoku.extensions.getCellsListWithSameNumber
import com.example.sudoku.extensions.setCellsRepeated
import org.jetbrains.annotations.TestOnly

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
        for (rowIndex in 0 until ROWS_IN_BOARD) {
            checkList(allCellsList.filter { cell -> cell.row == rowIndex })
        }
    }


    private fun checkColumns(allCellsList: List<Cell>) {
        for (columnIndex in 0 until COLUMNS_IN_BOARD) {
            checkList(allCellsList.filter { cell -> cell.column == columnIndex })
        }
    }

    private fun checkRectangles(allCellsList: List<Cell>) {
        for (rowIndex in 0 until COLUMNS_IN_BOARD step LINES_IN_RECT) {
            val subList = mutableListOf<Cell>()
            for (colIndex in 0 until ROWS_IN_BOARD step LINES_IN_RECT) {
                subList.addAll(allCellsList.filter { cell ->
                    cell.column in colIndex until LINES_IN_RECT + colIndex && cell.row in rowIndex until LINES_IN_RECT + rowIndex
                })
                checkList(subList)
                subList.clear()
            }
        }
    }

    private fun checkList(list: List<Cell>) {
        list.getCellsListWithSameNumber().toList().setCellsRepeated()
    }
}