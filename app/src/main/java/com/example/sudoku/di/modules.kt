package com.example.sudoku.di

import com.example.sudoku.GameActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { GameActivityViewModel() }
}