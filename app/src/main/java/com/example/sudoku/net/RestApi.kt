package com.example.sudoku.net

import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {
    @GET("board")
    suspend fun getSudokuValues(@Query("difficulty") difficulty: String): SudokuApiResponse
}