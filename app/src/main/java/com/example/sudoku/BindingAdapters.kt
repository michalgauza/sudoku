package com.example.sudoku

import android.widget.Button
import androidx.databinding.BindingAdapter

const val EMPTY_CELL_NUMBER = 0

@BindingAdapter("changeNum")
fun Button.changeNumber(viewModel: GameActivityViewModel) {
    this.setOnClickListener {
        val num = try {
            Integer.parseInt(this.text as String)
        } catch (e: NumberFormatException) {
            EMPTY_CELL_NUMBER
        }
        viewModel.updateSelectedCell(num)
    }
}