package com.frederic.millard.android.kata.gameoflife

import com.frederic.millard.android.kata.gameoflife.controller.GameOfLifeController
import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractorImpl
import com.frederic.millard.android.kata.gameoflife.core.NextGenerationCalculator
import com.frederic.millard.android.kata.gameoflife.core.World
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifeDisplay
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifePresenterImpl
import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository

class GameOfLifeActivity : GameOfLifeDisplay {

    private var controller: GameOfLifeController

    init {
        val repository = WorldRepository()
        val presenter = GameOfLifePresenterImpl(this)
        val nextGenerationCalculator = NextGenerationCalculator()
        val interactor = GameOfLifeInteractorImpl(presenter, repository, nextGenerationCalculator)
        controller = GameOfLifeController(interactor)
    }

    fun simulateInteraction() {
        controller.initWorld(height = 20, width = 100)

        controller.activateCell(x = 8, y = 8)
        controller.activateCell(x = 8, y = 7)
        controller.activateCell(x = 8, y = 9)
        controller.activateCell(x = 7, y = 8)
        controller.activateCell(x = 9, y = 8)
        //controller.activateCell(x = 9, y = 9)
        //controller.activateCell(x = 9, y = 10)
        //controller.activateCell(x = 9, y = 11)
        //controller.activateCell(x = 9, y = 12)

        controller.start(generationTime = 500L)
    }

    override fun displayWorld(world: World) {
        with(world) {
            println("Generation : $generationCount")
            cells.forEach {
                println(it.fold("", { acc, cell -> acc + if (cell) "O" else "-" }))
            }
        }
    }

}
