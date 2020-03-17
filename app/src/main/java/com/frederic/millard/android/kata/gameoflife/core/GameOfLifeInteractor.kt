package com.frederic.millard.android.kata.gameoflife.core

import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository


class GameOfLifeInteractor(
    private val presenter: GameOfLifePresenter,
    private val repository: WorldRepository,
    private val nextGenerationCalculator: NextGenerationCalculator) {

    fun initWorld(rowCount: Int, colCount: Int) {
        val initialWorld = World(
            rowCount = rowCount,
            colCount = colCount,
            generationCount = 0,
            cells = List(rowCount) { List(colCount) { false } }
        )
        repository.storeWorld(initialWorld)
        presenter.presentWorld(initialWorld)
    }

    fun toggleCell(row: Int, col: Int) {
        repository.getStoredWorld()?.let {
            val newCells = it.copy().cells
                    .map { row -> row.toMutableList() }
                    .apply { get(row)[col] = !get(row)[col] }
            val newWorld = it.copy(cells = newCells)
            repository.storeWorld(newWorld)
            presenter.presentWorld(newWorld)
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
