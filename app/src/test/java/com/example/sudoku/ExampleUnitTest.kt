package com.example.sudoku

import com.example.sudoku.extensions.getCellByRowAndColumn
import com.example.sudoku.extensions.getCellsListWithSameNumber
import com.example.sudoku.extensions.setCellsRepeated
import com.example.sudoku.extensions.toCellsList
import com.example.sudoku.models.Cell
import com.example.sudoku.utils.SudokuChecker
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testFunGetCellByRowAndColumn() {
        val cell1 = Cell(0, 0, 0, editable = false, isRepeated = false)
        val cell2 = Cell(1, 1, 0, editable = false, isRepeated = false)
        val cell3 = Cell(4, 5, 0, editable = false, isRepeated = false)
        val cell4 = Cell(9, 0, 0, editable = false, isRepeated = false)
        val testList = listOf(cell1, cell2, cell3, cell4)
        assertTrue("get cell1", testList.getCellByRowAndColumn(0, 0) == cell1)
        assertTrue("get cell4", testList.getCellByRowAndColumn(0, 9) == cell4)
        assertTrue("get null", testList.getCellByRowAndColumn(-1, -1) == null)
    }

    @Test
    fun testFunGetDuplicatesNumbersNoDuplicates() {
        val cell1 = Cell(0, 0, 1, editable = false, isRepeated = false)
        val cell2 = Cell(1, 1, 2, editable = false, isRepeated = false)
        val cell3 = Cell(4, 5, 3, editable = false, isRepeated = false)
        val cell4 = Cell(9, 0, 4, editable = false, isRepeated = false)
        val testList = listOf(cell1, cell2, cell3, cell4)
        assertTrue("return empty list", testList.getCellsListWithSameNumber().isEmpty())
    }

    @Test
    fun testFunGetDuplicatesNumbers() {
        val cell1 = Cell(0, 0, 1, editable = false, isRepeated = false)
        val cell2 = Cell(1, 1, 2, editable = false, isRepeated = false)
        val cell3 = Cell(4, 5, 3, editable = false, isRepeated = false)
        val cell4 = Cell(9, 0, 3, editable = false, isRepeated = false)
        val testList = listOf(cell1, cell2, cell3, cell4)
        val outputList = listOf(cell3, cell4)
        assertTrue(
            "return list same as output list",
            testList.getCellsListWithSameNumber().containsAll(outputList)
        )
    }

    @Test
    fun testFunIntListToCellsList() {
        val num1 = 1
        val num2 = 0
        val num3 = 9
        val num4 = 2

        val intList = listOf(num1, num2, num3, num4)

        val cell1 = Cell(0, 0, num1, false)
        val cell2 = Cell(1, 0, num2, true)
        val cell3 = Cell(2, 0, num3, false)
        val cell4 = Cell(3, 0, num4, false)

        val cellList = listOf(cell1, cell2, cell3, cell4)
        val outputList = intList.toCellsList(1, 4, 0)
        cellList.forEachIndexed { index, cell ->
            assertTrue("compare cells with the same index", cell == outputList[index])
        }
    }

    @Test
    fun testFunSetCellsToRepeated() {
        val cell1 = Cell(0, 0, 2, false)
        val cell2 = Cell(1, 0, 3, false)
        val cell3 = Cell(2, 0, 4, false)
        val cell4 = Cell(3, 0, 5, false)

        val cellList = listOf(cell1, cell2, cell3, cell4)
        cellList.setCellsRepeated()
        assertTrue("all cells set to repeated", cellList.all { cell -> cell.isRepeated })
    }
}
