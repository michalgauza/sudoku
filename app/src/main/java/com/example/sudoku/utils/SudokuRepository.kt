package com.example.sudoku.utils

import android.content.SharedPreferences
import com.example.sudoku.models.DifficultLevel
import com.example.sudoku.models.SudokuBoard
import com.example.sudoku.net.ResponseWrapper
import com.example.sudoku.net.RestApi
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

const val SUDOKU_BOARD_KEY = "sudokuBoardKey"

class SudokuRepository(private val api: RestApi, private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    fun loadSudokuBoard(): SudokuBoard? {
        sharedPreferences.getString(SUDOKU_BOARD_KEY, null)?.let { string ->
            return gson.fromJson(string, SudokuBoard::class.java)
        }
        return null
    }

    fun saveSudokuBoard(sudokuBoard: SudokuBoard): Boolean {
        gson.toJson(sudokuBoard).let { string ->
            return sharedPreferences.edit().putString(SUDOKU_BOARD_KEY, string).commit()
        }
    }

    suspend fun getSudokuValues(difficultLevel: DifficultLevel) =
        safeCall { api.getSudokuValues(difficultLevel.difficult) }

    private suspend fun <T> safeCall(apiCall: suspend () -> T): ResponseWrapper<T> {
        return try {
            ResponseWrapper.Success(apiCall.invoke())
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> ResponseWrapper.NetworkError(
                    exception.message
                )
                is HttpException -> {
                    ResponseWrapper.GenericError(
                        exception.code(),
                        exception.message
                    )
                }
                else -> ResponseWrapper.GenericError(
                    null,
                    exception.message
                )
            }
        }
    }
}