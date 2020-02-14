package com.frederic.millard.android.kata.gameoflife.core

class NextGenerationCalculator {

    companion object {
        private val AROUND_COUNT_FOR_LIVING = listOf(3)
        private val AROUND_COUNT_FOR_DYING = listOf(2, 3)
    }

    fun computeNextGeneration(actualWorld: World): World {
        with(actualWorld) {
            val newCells = List(height) { row ->
                List(width) { col ->
                    val livingCellAround = livingCellAroundCount(this, row, col)
                    val cell = cells[row][col]
                    Cell(
                        if (cell.isLiving) AROUND_COUNT_FOR_DYING.contains(livingCellAround)
                        else AROUND_COUNT_FOR_LIVING.contains(livingCellAround))
                }
            }
            return actualWorld.copy(generationCount = generationCount + 1, cells = newCells)
        }
    }

    private fun livingCellAroundCount(world: World, row: Int, col: Int): Int {
        return with(world) {
            listOf(
                row - 1 to col - 1,
                row - 1 to col,
                row - 1 to col + 1,
                row to col - 1,
                row to col + 1,
                row + 1 to col - 1,
                row + 1 to col,
                row + 1 to col + 1)
                    .filter { it.first in 0 until height && it.second in 0 until width }
                    .map { cells[it.first][it.second] }
                    .count { it.isLiving }
        }
    }

}