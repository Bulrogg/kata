package com.frederic.millard.android.kata.gameoflife.core

class NextGenerationCalculator(private var livingCellAroundCalculator: LivingCellAroundCalculator) {

    fun computeNextGeneration(world: World): World {
        val newCells = List(world.height) { row ->
            List(world.width) { col ->
                TODO("Implem 2")
            }
        }
        return world.copy(
            generationCount = world.generationCount + 1,
            cells = newCells
        )
    }
}