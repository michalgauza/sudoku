package com.example.sudoku.utils

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.sudoku.activities.GameActivityViewModel

@BindingAdapter("changeNum")
fun Button.changeNumber(viewModel: GameActivityViewModel) {
    this.setOnClickListener {
        val num = try {
            Integer.parseInt(this.text as String)
        } catch (e: NumberFormatException) {
            viewModel.clear()
            EMPTY_CELL_NUMBER
        }
        viewModel.updateSelectedCell(num)
    }
}

@BindingAdapter("isVisible")
fun View.isVisible(visible: Boolean) {
    if (visible) this.visibility = View.VISIBLE else this.visibility = View.GONE
}