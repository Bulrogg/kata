package com.frederic.millard.android.kata.gameoflife.core

import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository


class GameOfLifeInteractor(
    private val presenter: GameOfLifePresenter,
    private val repository: WorldRepository,
    private val nextGenerationCalculator: NextGenerationCalculator) {

    fun initWorld(height: Int, width: Int) {
        val initialWorld = World(
            height = height,
            width = width,
            generationCount = 0,
            cells = List(height) { List(width) { false } }
        )
        repository.storeWorld(initialWorld)
        presenter.presentWorld(initialWorld)
    }

    fun activateCell(x: Int, y: Int) {
        val actualWorld = repository.getStoredWorld()
        actualWorld?.let {
            val newCells = it.copy().cells
                    .map { row -> row.toMutableList() }
                    .apply { get(x)[y] = true }
            repository.storeWorld(it.copy(cells = newCells))
        }
    }

    fun nextGeneration() {
        val actualWorld = repository.getStoredWorld()
        actualWorld?.let {
            val newWorld = nextGenerationCalculator.computeNextGeneration(it)
            repository.storeWorld(newWorld)
            presenter.presentWorld(newWorld)
        }
    }
}
