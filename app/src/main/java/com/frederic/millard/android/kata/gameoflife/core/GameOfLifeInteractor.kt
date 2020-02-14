package com.frederic.millard.android.kata.gameoflife.core

import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository

interface GameOfLifeInteractor {
    fun initWorld(height: Int, width: Int)
    fun activateCell(x: Int, y: Int)
    fun nextGeneration()
}

interface GameOfLifePresenter {
    fun presentWorld(world: World)
}

class GameOfLifeInteractorImpl(
    private val presenter: GameOfLifePresenter,
    private val repository: WorldRepository,
    private val nextGenerationCalculator: NextGenerationCalculator) : GameOfLifeInteractor {

    override fun initWorld(height: Int, width: Int) {
        val initialWorld = World(
            height = height,
            width = width,
            generationCount = 0,
            cells = List(height) { List(width) { false } }
        )
        repository.storeWorld(initialWorld)
        presenter.presentWorld(initialWorld)
    }

    override fun activateCell(x: Int, y: Int) {
        val actualWorld = repository.getStoredWorld()
        actualWorld?.let {
            val newCells = it.copy().cells
                    .map { row -> row.toMutableList() }
                    .apply { get(x)[y] = true }
            repository.storeWorld(it.copy(cells = newCells))
        }
    }

    override fun nextGeneration() {
        val actualWorld = repository.getStoredWorld()
        actualWorld?.let {
            val newWorld = nextGenerationCalculator.computeNextGeneration(it)
            repository.storeWorld(newWorld)
            presenter.presentWorld(newWorld)
        }
    }
}
