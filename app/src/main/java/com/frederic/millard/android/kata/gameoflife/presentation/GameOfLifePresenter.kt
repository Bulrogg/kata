package com.frederic.millard.android.kata.gameoflife.presentation

import com.frederic.millard.android.kata.gameoflife.core.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.core.World

interface GameOfLifeDisplay {
    fun displayWorld(world: World)
}

class GameOfLifePresenterImpl(private val display: GameOfLifeDisplay) : GameOfLifePresenter {

    override fun presentWorld(world: World) {
        display.displayWorld(world)
    }

}