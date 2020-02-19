package com.frederic.millard.android.kata.gameoflife.core

class NextGenerationCalculator(private var livingCellAroundCalculator: LivingCellAroundCalculator) {

    companion object {
        private val AROUND_LIVING_CELL_COUNT_TO_CONTINUE_LIVING = listOf(2, 3)
        private val AROUND_LIVING_CELL_COUNT_TO_BE_BORN = listOf(3)
    }

    fun computeNextGeneration(world: World): World {
        val newCells = List(world.height) { row ->
            List(world.width) { col ->
                val livingCellAround = livingCellAroundCalculator.computeLivingCellsAround(world, row, col)
                val cell = world.cells[row][col]
                if (cell) AROUND_LIVING_CELL_COUNT_TO_CONTINUE_LIVING.contains(livingCellAround)
                else AROUND_LIVING_CELL_COUNT_TO_BE_BORN.contains(livingCellAround)
            }
        }
        return world.copy(
            generationCount = world.generationCount + 1,
            cells = newCells
        )
    }
}