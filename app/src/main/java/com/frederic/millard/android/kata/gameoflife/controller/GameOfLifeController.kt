package com.frederic.millard.android.kata.gameoflife.controller

import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractor
import java.util.*

class GameOfLifeController(private val interactor: GameOfLifeInteractor) {

    private val timer = Timer()

    fun initWorld(height: Int, width: Int) {
        interactor.initWorld(height, width)
    }

    fun activateCell(x: Int, y: Int) {
        interactor.activateCell(x, y)
    }

    fun start(generationTime: Long) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                interactor.nextGeneration()
            }
        }, 0, generationTime)
    }
}