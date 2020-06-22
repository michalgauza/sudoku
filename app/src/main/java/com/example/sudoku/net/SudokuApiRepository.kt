package com.example.sudoku.net

import retrofit2.HttpException
import java.io.IOException

class SudokuApiRepository(private val api: RestApi) {

    suspend fun getSudokuValues() = safeCall { api.getSudokuValues() }

    private suspend fun <T> safeCall(apiCall: suspend () -> T): ResponseWrapper<T> {
        return try {
            ResponseWrapper.Success(apiCall.invoke())
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> ResponseWrapper.NetworkError(exception.message)
                is HttpException -> {
                    ResponseWrapper.GenericError(exception.code(), exception.message)
                }
                else -> ResponseWrapper.GenericError(null, exception.message)
            }
        }
    }
}