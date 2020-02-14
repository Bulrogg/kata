package com.frederic.millard.android.kata.gameoflife.presentation

import com.frederic.millard.android.kata.gameoflife.core.World

interface GameOfLifeDisplay {
    fun displayWorld(world: World)
}

class GameOfLifePresenter(private val display: GameOfLifeDisplay) {

    fun presentWorld(world: World) {
        display.displayWorld(world)
    }

}