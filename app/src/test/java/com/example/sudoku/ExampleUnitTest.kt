package com.example.sudoku

import com.example.sudoku.extensions.getCellByRowAndColumn
import com.example.sudoku.extensions.getCellsListWithSameNumber
import com.example.sudoku.models.Cell
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testGetCellByRowAndColumn() {
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
    fun testGetDuplicatesNumbersNoDuplicates() {
        val cell1 = Cell(0, 0, 1, editable = false, isRepeated = false)
        val cell2 = Cell(1, 1, 2, editable = false, isRepeated = false)
        val cell3 = Cell(4, 5, 3, editable = false, isRepeated = false)
        val cell4 = Cell(9, 0, 4, editable = false, isRepeated = false)
        val testList = listOf(cell1, cell2, cell3, cell4)
        assertTrue("return empty list", testList.getCellsListWithSameNumber().isEmpty())
    }

    @Test
    fun testGetDuplicatesNumbers() {
        val cell1 = Cell(0, 0, 1, editable = false, isRepeated = false)
        val cell2 = Cell(1, 1, 2, editable = false, isRepeated = false)
        val cell3 = Cell(4, 5, 3, editable = false, isRepeated = false)
        val cell4 = Cell(9, 0, 3, editable = false, isRepeated = false)
        val testList = listOf(cell1, cell2, cell3, cell4)
        val outputList = listOf(cell3, cell4)
        println(testList.getCellsListWithSameNumber())
        assertTrue("return list same as output list", testList.getCellsListWithSameNumber().containsAll(outputList))
    }

    @Test
    fun testToCellsList(){
        val cell1 = Cell(0, 0, 1, false)
        val cell2 = Cell(1, 0, 0, true)
        val cell3 = Cell(2, 0, 0, true)

    }
}
