package com.frederic.millard.android.kata.gameoflife

import com.frederic.millard.android.kata.gameoflife.controller.GameOfLifeController
import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractor
import com.frederic.millard.android.kata.gameoflife.core.LivingCellAroundCalculator
import com.frederic.millard.android.kata.gameoflife.core.NextGenerationCalculator
import com.frederic.millard.android.kata.gameoflife.core.World
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifeDisplay
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository

class GameOfLifeActivity : GameOfLifeDisplay {

    private var controller: GameOfLifeController

    init {
        val repository = WorldRepository()
        val presenter = GameOfLifePresenter(this)
        val nextGenerationCalculator = NextGenerationCalculator(LivingCellAroundCalculator())
        val interactor = GameOfLifeInteractor(presenter, repository, nextGenerationCalculator)
        controller = GameOfLifeController(interactor)
    }

    fun simulateInteraction() {
        controller.initWorld(rowCount = 20, colCount = 20)

        controller.toggleCell(row = 8, col = 8)
        controller.toggleCell(row = 8, col = 7)
        controller.toggleCell(row = 8, col = 9)
        controller.toggleCell(row = 7, col = 8)
        controller.toggleCell(row = 9, col = 8)

        //controller.toggleCell(row = 9, col = 9)
        //controller.toggleCell(row = 9, col = 10)
        //controller.toggleCell(row = 9, col = 11)
        //controller.toggleCell(row = 9, col = 12)

        controller.changeGenerationTimeValue(700L)
        controller.start()
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
