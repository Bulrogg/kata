package com.frederic.millard.android.kata.gameoflife.controller

import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractor
import java.util.*

class GameOfLifeController(private val interactor: GameOfLifeInteractor) {

    private val generationTimer = GenerationTimer(700L) { interactor.nextGeneration() }

    fun initWorld(rowCount: Int, colCount: Int) {
        interactor.initWorld(rowCount, colCount)
    }

    fun activateCell(row: Int, col: Int) {
        interactor.activateCell(row, col)
    }

    fun changeGenerationTimeValue(generationTime: Long) {
        generationTimer.changeGenerationTimeValue(generationTime)
    }

    fun start() {
        generationTimer.startGeneration()
    }

    fun stop() {
        generationTimer.pauseGeneration()
    }
}

private class GenerationTimer(private var generationTime: Long, private val runFunction: () -> Unit) {

    private val timer = Timer()
    private var generationRunning = false

    fun startGeneration() {
        generationRunning = true
    }

    fun pauseGeneration() {
        generationRunning = false
    }

    fun changeGenerationTimeValue(generationTime: Long) {
        this.generationTime = generationTime
    }

    init {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (generationRunning) runFunction()
            }
        }, 0, generationTime)
    }

}