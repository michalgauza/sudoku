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

const val BASE_URL = "https://sugoku.herokuapp.com/"

val viewModelsModule = module {
    viewModel { GameActivityViewModel(get()) }
}

val restApiModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideRestApi(get()) }
    single { provideGson() }

}

val sharedPreferencesModule = module {
    single { provideSharedPreferences(get()) }
}

val repositoryModule = module {
    single { SudokuRepository(get(), get(), get()) }
}

private fun provideGson():Gson = Gson()

private fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()


private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson().newBuilder().create()))
        .build()


private fun provideRestApi(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)

private fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(SUDOKU_BOARD_KEY, Context.MODE_PRIVATE)
