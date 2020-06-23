package com.example.sudoku.di

import android.content.Context
import android.content.SharedPreferences
import com.example.sudoku.activities.GameActivityViewModel
import com.example.sudoku.utils.SUDOKU_BOARD_KEY
import com.example.sudoku.net.RestApi
import com.example.sudoku.utils.SudokuRepository
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

}

val sharedPreferencesModule = module {
    single { provideSharedPreferences(get()) }
}

val repositoryModule = module {
    single { SudokuRepository(get(), get()) }
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

private fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(SUDOKU_BOARD_KEY, Context.MODE_PRIVATE)
}