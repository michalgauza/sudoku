package com.example.sudoku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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


    val listOfValues = listOfListOfCellsValues.flatten()
    val cellsList = List(listOfValues.size) { i ->
        Cell(
            i % 9,
            i / 9,
            listOfValues[i],
            listOfValues[i] == 0
        )
    }

//    val cellsListLiveData = liveData {
//        emit(cellsList)
//    }

//    private val _selectedCellLiveData = MutableLiveData<Cell>()
//    val selectedCellLiveData: LiveData<Cell>
//        get() = _selectedCellLiveData

    private val _cellsListLiveData = MutableLiveData<List<Cell>>()
    val cellsListLiveData: LiveData<List<Cell>>
        get() = _cellsListLiveData

    var selectedCell: Cell? = null

    init {
        setupCellsNumbers()
    }

    private fun setupCellsNumbers(){
        _cellsListLiveData.value = cellsList
    }

    fun setSelectedCell(row: Int, column: Int) {
        selectedCell =
            cellsListLiveData.value?.getCellByRowAndColumn(row, column)
    }

    fun updateSelectedCell(newNum: Int) {
        selectedCell?.let {
            if (it.editable) selectedCell?.number = newNum
            val tmp = cellsListLiveData.value
            tmp?.getCellByRowAndColumn(it.row, it.column).apply { it.number = newNum }
            _cellsListLiveData.value = tmp
        }

    }

    fun checkRow() {
        val tmp = cellsListLiveData.value?.subList(0, 8)

    }

}