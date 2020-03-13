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

    private fun initAndStartGameOfLife() {
        controller.initWorld(height = 20, width = 20)

        controller.activateCell(x = 8, y = 8)
        controller.activateCell(x = 8, y = 7)
        controller.activateCell(x = 8, y = 9)
        controller.activateCell(x = 7, y = 8)
        controller.activateCell(x = 9, y = 8)

        controller.activateCell(x = 9, y = 9)
        controller.activateCell(x = 9, y = 10)
        controller.activateCell(x = 9, y = 11)
        controller.activateCell(x = 9, y = 12)

        controller.start(generationTime = 1000L)
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
