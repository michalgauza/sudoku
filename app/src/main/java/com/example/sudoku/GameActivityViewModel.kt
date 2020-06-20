package com.example.sudoku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sudoku.ui.CELLS_IN_LINE

class GameActivityViewModel : ViewModel() {


    //todo przychodzi z api
    private val listOfListOfCellsValues = listOf(
        listOf(0, 0, 0, 0, 0, 0, 8, 0, 1),
        listOf(1, 0, 4, 3, 6, 8, 0, 0, 0),
        listOf(0, 0, 9, 0, 4, 7, 2, 0, 0),
        listOf(2, 0, 3, 0, 0, 0, 7, 0, 8),
        listOf(0, 5, 8, 0, 9, 1, 0, 6, 0),
        listOf(0, 0, 6, 2, 8, 0, 1, 5, 0),
        listOf(0, 3, 0, 8, 7, 0, 0, 0, 5),
        listOf(8, 0, 0, 9, 3, 0, 6, 0, 0),
        listOf(9, 0, 0, 0, 0, 2, 0, 8, 3)
    )

    private val _cellsListLiveData = MutableLiveData<List<Cell>>()
    val cellsListLiveData: LiveData<List<Cell>>
        get() = _cellsListLiveData

    private var selectedCell: Cell? = null

    init {
        setupCellsNumbers()
    }

    private fun setupCellsNumbers() {
        setupCellsList()
    }

    private fun setupCellsList() {
        listOfListOfCellsValues.flatten().also {
            _cellsListLiveData.value = List(it.size) { i ->
                Cell(
                    i % CELLS_IN_LINE,
                    i / CELLS_IN_LINE,
                    it[i],
                    it[i] == 0
                )
            }
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

    fun checkRow(row: List<Cell>) {
        row.getDuplicate().let { duplicates ->
            duplicates.forEach { duplicatedCell ->
                if (duplicatedCell.editable && duplicatedCell.number != 0)
                    duplicatedCell.isRepeated = true
            }
        }

    }

    fun checkBoard() {
        _cellsListLiveData.mutation {
            it.value?.filter { cell -> cell.isRepeated }
                ?.forEach { cell -> cell.isRepeated = false }

            it.value?.let { cellsList ->
//                for (i in 0..8){
//                    val rowIndex = 9 * i
                checkRow(cellsList.subList(0, 9))
//                }
            }
        }
    }

}