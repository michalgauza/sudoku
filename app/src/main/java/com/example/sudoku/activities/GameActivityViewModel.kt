package com.example.sudoku.activities

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.example.sudoku.R
import com.example.sudoku.models.Cell
import com.example.sudoku.models.DifficultLevel
import com.example.sudoku.models.SudokuBoard
import com.example.sudoku.net.ResponseWrapper
import com.example.sudoku.net.SudokuApiResponse
import com.example.sudoku.utils.SingleLiveEvent
import com.example.sudoku.extensions.mutation
import com.example.sudoku.extensions.toCellsList
import com.example.sudoku.utils.EMPTY_CELL_NUMBER
import com.example.sudoku.utils.SudokuChecker
import com.example.sudoku.utils.SudokuRepository
import com.example.sudoku.views.CELLS_IN_LINE
import com.example.sudoku.views.CELLS_IN_ROW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameActivityViewModel(private val repo: SudokuRepository) : ViewModel() {

    private val sudokuChecker = SudokuChecker()

    val localGameMessageSingleLiveEvent =
        SingleLiveEvent<@StringRes Int>()

    private val _errorStringResLiveData = MutableLiveData<@StringRes Int>()
    val errorStringResLiveData: LiveData<Int>
        get() = _errorStringResLiveData

    private val _showLoadingLiveData = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean>
        get() = _showLoadingLiveData

    private val _winLiveData = MutableLiveData<Boolean>()
    val winLiveData: LiveData<Boolean>
        get() = _winLiveData

    private val _boardLiveData = MutableLiveData<SudokuBoard>()
    val boardLiveData: LiveData<SudokuBoard>
        get() = _boardLiveData

    private var selectedCell: Cell? = null

    fun setupSudokuBoard(isContinue: Boolean, difficultLevel: DifficultLevel) {
        _showLoadingLiveData.value = true
        viewModelScope.launch {
            var message = R.string.no_saved_game
            if (isContinue) {
                repo.loadSudokuBoard()?.let { sudokuBoard ->
                    _boardLiveData.value = sudokuBoard
                    message = R.string.load_saved_game
                } ?: createSudokuBoard(difficultLevel)
            } else {
                createSudokuBoard(difficultLevel)
                message = R.string.new_game
            }
            _showLoadingLiveData.postValue(false)
            localGameMessageSingleLiveEvent.value = message
        }
    }

    private suspend fun getRestSudokuNumbers(difficultLevel: DifficultLevel): SudokuApiResponse? {
        when (val response = repo.getSudokuValues(difficultLevel)) {
            is ResponseWrapper.Success -> return response.value
            is ResponseWrapper.GenericError -> _errorStringResLiveData.postValue(R.string.generic_error)
            is ResponseWrapper.NetworkError -> _errorStringResLiveData.postValue(R.string.network_error)
        }
        return null
    }

    private suspend fun createSudokuBoard(difficultLevel: DifficultLevel) {
        getRestSudokuNumbers(difficultLevel)?.let { apiResponse ->
            _boardLiveData.value = SudokuBoard(
                apiResponse.board.flatten()
                    .toCellsList(CELLS_IN_LINE, CELLS_IN_LINE, EMPTY_CELL_NUMBER)
            )
        }
    }

    fun setSelectedCell(cell: Cell?) {
        selectedCell = cell
    }

    fun updateSelectedCell(newNum: Int) {
        selectedCell?.let { selectedCell ->
            if (selectedCell.editable) {
                _boardLiveData.mutation { liveData ->
                    liveData.value?.cellsList?.find { cell -> cell == this.selectedCell }
                        ?.let { cell ->
                            cell.number = newNum
                            cell.isRepeated = false
                        }
                }
            }
        }
    }

    fun checkBoard() {
        _boardLiveData.mutation { liveData ->
            _winLiveData.value = sudokuChecker.checkBoard(liveData.value)
        }
    }

    fun saveGame() {
        viewModelScope.launch(Dispatchers.IO) {
            _boardLiveData.value?.let { sudokuBoard ->
                val message = if (repo.saveSudokuBoard(sudokuBoard)) {
                    R.string.save_successful
                } else {
                    R.string.save_fail
                }
                localGameMessageSingleLiveEvent.postValue(message)
            }
        }
    }
}