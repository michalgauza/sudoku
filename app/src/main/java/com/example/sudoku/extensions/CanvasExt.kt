package com.example.sudoku.extensions

import android.graphics.Canvas
import android.graphics.Paint

fun Canvas.fillCellWithPaint(row: Int, column: Int, cellSize: Float, paint: Paint) {
    this.drawRect(
        column.toFloat() * cellSize,
        row * cellSize + cellSize,
        column * cellSize + cellSize,
        row.toFloat() * cellSize,
        paint
    )
}