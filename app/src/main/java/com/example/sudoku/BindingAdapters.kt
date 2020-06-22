package com.example.sudoku

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.sudoku.models.SudokuChecker

const val EMPTY_CELL_NUMBER = 0

@BindingAdapter("changeNum")
fun Button.changeNumber(sudokuBoardModel: SudokuChecker) {
    this.setOnClickListener {
        val num = try {
            Integer.parseInt(this.text as String)
        } catch (e: NumberFormatException) {
            EMPTY_CELL_NUMBER
        }
        sudokuBoardModel.updateSelectedCell(num)
    }
}

@BindingAdapter("isVisible")
fun View.isVisible(visible: Boolean) {
    if (visible) this.visibility = View.VISIBLE else this.visibility = View.GONE
}