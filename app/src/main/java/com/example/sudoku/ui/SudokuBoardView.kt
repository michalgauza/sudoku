package com.example.sudoku.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.sudoku.Cell
import com.example.sudoku.fillCellWithPaint

const val DRAW_START_VALUE = 0f
const val CELLS_IN_LINE = 9
const val CELLS_IN_RECT = 3
const val NO_ROW_SELECTED = -1
const val NO_COLUMN_SELECTED = -1
const val DEFAULT_CELL_SIZE = 0f

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4f
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2f
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private val uneditableCellPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = 40f
    }

    private val cellsList = mutableListOf<Cell>()

    private var cellSize = DEFAULT_CELL_SIZE
    private var selectedRow = NO_ROW_SELECTED
    private var selectedColumn = NO_COLUMN_SELECTED

    var cellTouchListener: ((Pair<Int, Int>) -> Unit)? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        widthMeasureSpec.coerceAtMost(heightMeasureSpec).let {
            setMeasuredDimension(it, it)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            selectedRow = (event.y / cellSize).toInt()
            selectedColumn = (event.x / cellSize).toInt()
            cellTouchListener?.invoke(Pair(selectedRow, selectedColumn))
            invalidate()
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        cellSize = (width / CELLS_IN_LINE).toFloat()
        if (canvas != null) {
            drawLines(canvas)
            fillSelectedCell(canvas)
            fillUneditableCells(canvas)
            drawNumbers(canvas)
        }

    }

    private fun drawLines(canvas: Canvas) {
        with(canvas) {
            drawRect(
                DRAW_START_VALUE,
                DRAW_START_VALUE,
                width.toFloat(),
                height.toFloat(),
                thickLinePaint
            )
            for (i in 1..CELLS_IN_LINE) {
                drawLine(
                    i * cellSize,
                    DRAW_START_VALUE,
                    i * cellSize,
                    height.toFloat(),
                    getLinePaint(i)
                )
                drawLine(
                    DRAW_START_VALUE,
                    i * cellSize,
                    width.toFloat(),
                    i * cellSize,
                    getLinePaint(i)
                )
            }
        }
    }

    private fun fillSelectedCell(canvas: Canvas) {
        canvas.fillCellWithPaint(selectedRow, selectedColumn, cellSize, selectedCellPaint)
    }

    private fun fillUneditableCells(canvas: Canvas) {
        cellsList.filter { cell -> !cell.editable }.forEach {
            canvas.fillCellWithPaint(it.row, it.column, cellSize, uneditableCellPaint)
        }
    }

    private fun drawNumbers(canvas: Canvas) {
        cellsList.forEach {
            val textBounds = Rect()
            textPaint.getTextBounds(it.valueToString(), 0, it.valueToString().length, textBounds)
            val textWidth = textPaint.measureText(it.valueToString())
            val textHeight = textBounds.height()

            canvas.drawText(
                it.valueToString(),
                it.column * cellSize + cellSize / 2 - textWidth / 2,
                it.row * cellSize + cellSize / 2 + textHeight / 2,
                textPaint
            )
        }
    }

    private fun getLinePaint(index: Int): Paint =
        if (index % CELLS_IN_RECT == 0) thickLinePaint else thinLinePaint

    fun updateCellsList(newList: List<Cell>) {
        cellsList.clear()
        cellsList.addAll(newList)
        invalidate()
    }
}