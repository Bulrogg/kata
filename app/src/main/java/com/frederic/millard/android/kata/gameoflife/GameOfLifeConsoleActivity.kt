package com.frederic.millard.android.kata.gameoflife

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frederic.millard.android.kata.R
import com.frederic.millard.android.kata.gameoflife.controller.GameOfLifeController
import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractor
import com.frederic.millard.android.kata.gameoflife.core.LivingCellAroundCalculator
import com.frederic.millard.android.kata.gameoflife.core.NextGenerationCalculator
import com.frederic.millard.android.kata.gameoflife.core.World
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifeDisplay
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository
import kotlinx.android.synthetic.main.activity_game_of_life_console.*

class GameOfLifeConsoleActivity : AppCompatActivity(), GameOfLifeDisplay {

    companion object {
        fun getIntent(context: Context) = Intent(context, GameOfLifeConsoleActivity::class.java)
    }

    private lateinit var controller: GameOfLifeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_of_life_console)

        injectObject()
        initAndStartGameOfLife()
    }

    override fun onStop() {
        super.onStop()
        controller.stop()
    }

    private fun initAndStartGameOfLife() {
        controller.initWorld(rowCount = 32, colCount = 30)

        controller.activateCell(row = 8, col = 8)
        controller.activateCell(row = 8, col = 7)
        controller.activateCell(row = 8, col = 9)
        controller.activateCell(row = 7, col = 8)
        controller.activateCell(row = 9, col = 8)

        controller.activateCell(row = 9, col = 9)
        controller.activateCell(row = 9, col = 10)
        controller.activateCell(row = 9, col = 11)
        controller.activateCell(row = 9, col = 12)

        controller.changeGenerationTimeValue(1000L)
        controller.start()
    }

    private fun injectObject() {
        val repository = WorldRepository()
        val presenter = GameOfLifePresenter(this)
        val nextGenerationCalculator = NextGenerationCalculator(LivingCellAroundCalculator())
        val interactor = GameOfLifeInteractor(presenter, repository, nextGenerationCalculator)
        controller = GameOfLifeController(interactor)
    }

    override fun displayWorld(world: World) {
        screenContentTextView.text =
            with(world) {
                StringBuilder().apply {
                    append("Generation : $generationCount")
                    append("\n")
                    cells.forEach {
                        append(it.fold("", { acc, cell -> acc + if (cell) "O" else "-" }))
                        append("\n")
                    }
                }
            }
    }
}
