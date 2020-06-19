package com.example.sudoku

import android.graphics.Canvas
import android.graphics.Paint

fun Canvas.fillCellWithPaint(row: Int, column: Int, cellSize: Float, paint: Paint) {
    this.drawRect(
        column.toFloat() * cellSize + 1,
        row * cellSize + cellSize - 1,
        column * cellSize + cellSize - 1,
        row.toFloat() * cellSize + 1,
        paint
    )
}