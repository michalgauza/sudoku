package com.example.sudoku

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.example.sudoku.models.SudokuChecker
import com.example.sudoku.net.ResponseWrapper
import com.example.sudoku.net.SudokuApiRepository
import com.example.sudoku.net.SudokuApiResponse
import kotlinx.coroutines.launch

class GameActivityViewModel(private val repo: SudokuApiRepository) : ViewModel() {

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

    val sudokuBoard = SudokuChecker()

    private val _errorStringLiveData = MutableLiveData<@StringRes Int>()
    val responseLiveData: LiveData<Int>
        get() = _errorStringLiveData

    private val _showLoadingLiveData = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean>
        get() = _showLoadingLiveData

    fun getRestSudokuValues() {
        _showLoadingLiveData.value = true
        viewModelScope.launch {
            when (val response = repo.getSudokuValues()) {
                is ResponseWrapper.Success -> setupSudokuBoard(response.value)
                is ResponseWrapper.GenericError -> _errorStringLiveData.postValue(R.string.generic_error)
                is ResponseWrapper.NetworkError -> _errorStringLiveData.postValue(R.string.network_error)
            }
            _showLoadingLiveData.postValue(false)
        }
    }

    fun setupSudokuBoard(sudokuResponse: SudokuApiResponse) {
        sudokuBoard.setupCellsNumbers(sudokuResponse.board.flatten())
    }




}