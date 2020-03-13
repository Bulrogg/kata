package com.frederic.millard.android.kata.gameoflife.core

class NextGenerationCalculator(private var livingCellAroundCalculator: LivingCellAroundCalculator) {

    companion object {
        private val NUMBERS_OF_LIVING_NEIGHBOURS_TO_SURVIVE = listOf(2, 3)
        private val NUMBERS_OF_LIVING_NEIGHBOURS_TO_BORN = listOf(3)

    }

    fun computeNextGeneration(world: World): World {
        return with(world) { copy(generationCount = generationCount + 1, cells = computeNewCells(world)) }
    }

    private fun World.computeNewCells(world: World) =
        List(height) { row ->
            List(width) { col ->
                val cellIsLiving = cells[row][col]
                val livingNeighboursCount = livingCellAroundCalculator.computeLivingCellsAround(world, row, col)
                if (cellIsLiving) NUMBERS_OF_LIVING_NEIGHBOURS_TO_SURVIVE.contains(livingNeighboursCount)
                else NUMBERS_OF_LIVING_NEIGHBOURS_TO_BORN.contains(livingNeighboursCount)
            }
        }
}