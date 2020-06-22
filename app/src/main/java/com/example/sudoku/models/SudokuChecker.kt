package com.example.sudoku.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sudoku.EMPTY_CELL_NUMBER
import com.example.sudoku.getDuplicatedNumbers
import com.example.sudoku.mutation
import com.example.sudoku.setCellsRepeated
import com.example.sudoku.ui.*

class SudokuChecker {

    private val _cellsListLiveData = MutableLiveData<List<Cell>>()
    val cellsListLiveData: LiveData<List<Cell>>
        get() = _cellsListLiveData

    private var selectedCell: Cell? = null

    fun setupCellsNumbers(list: List<Int>) {
        _cellsListLiveData.value = List(list.size) { i ->
            Cell(
                i % CELLS_IN_LINE,
                i / CELLS_IN_LINE,
                list[i],
                list[i] == EMPTY_CELL_NUMBER
            )
        }
    }

    fun setSelectedCell(cell: Cell?) {
        selectedCell = cell
    }

    fun updateSelectedCell(newNum: Int) {
        selectedCell?.let { cell ->
            if (cell.editable) {
                _cellsListLiveData.mutation {
                    it.value?.find { cell -> cell == selectedCell }?.number = newNum
                }
            }
        }
    }

    fun checkBoard() {
        _cellsListLiveData.mutation {
            it.value?.let { cellsList ->
                setAllCellsToNotRepeated(cellsList)
                checkRows(cellsList)
                checkColumns(cellsList)
                checkRectangles(cellsList)
            }
        }
    }

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
        row.getDuplicatedNumbers().toList().setCellsRepeated()
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
        column.getDuplicatedNumbers().toList().setCellsRepeated()
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
        rectangle.getDuplicatedNumbers().toList().setCellsRepeated()
    }
}