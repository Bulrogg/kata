package com.frederic.millard.android.kata.gameoflife.core

class LivingCellAroundCalculator {

    fun computeLivingCellsAround(world: World, row: Int, col: Int): Int {
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
                    .filter { it.first in 0 until rowCount && it.second in 0 until colCount }
                    .count { cells[it.first][it.second] }
        }
    }
}