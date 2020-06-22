package com.example.sudoku.di

import com.example.sudoku.GameActivityViewModel
import com.example.sudoku.net.RestApi
import com.example.sudoku.net.SudokuApiRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelsModule = module {
    viewModel { GameActivityViewModel(get()) }
}

val restApiModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideRestApi(get()) }
    single { SudokuApiRepository(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://sugoku.herokuapp.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson().newBuilder().create()))
        .build()
}

private fun provideRestApi(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)