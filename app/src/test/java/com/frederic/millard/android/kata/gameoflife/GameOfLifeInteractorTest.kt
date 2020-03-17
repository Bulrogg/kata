package com.frederic.millard.android.kata.gameoflife

import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractor
import com.frederic.millard.android.kata.gameoflife.core.NextGenerationCalculator
import com.frederic.millard.android.kata.gameoflife.core.World
import com.frederic.millard.android.kata.gameoflife.presentation.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.repository.WorldRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameOfLifeInteractorTest {

    @Mock private lateinit var presenter: GameOfLifePresenter
    @Mock private lateinit var repository: WorldRepository
    @Mock private lateinit var nextGenerationCalculator: NextGenerationCalculator

    @InjectMocks private lateinit var interactor: GameOfLifeInteractor

    @Test
    fun `initWorld should present an empty world`() {
        // Given
        val height = 3
        val width = 2

        // When
        interactor.initWorld(height, width)

        // Then
        val world = World(
            rowCount = 3,
            colCount = 2,
            generationCount = 0,
            cells = List(3) { List(2) { false } })
        then(repository).should().storeWorld(world)
        then(presenter).should().presentWorld(world)
    }

    @Test
    fun `toggleCell when cell unactived cell should activate the corresponding cell`() {
        // Given
        val world = World(
            rowCount = 2,
            colCount = 3,
            generationCount = 2,
            cells = List(3) { List(3) { false } })
        given(repository.getStoredWorld()).willReturn(world)

        // When
        interactor.toggleCell(1, 1)

        // Then
        val newCells = world.copy().cells
                .map { it.toMutableList() }
                .apply { get(1)[1] = true }
        val expectedNewWorld = world.copy(cells = newCells)

        then(repository).should().storeWorld(expectedNewWorld)
        then(presenter).should().presentWorld(expectedNewWorld)
    }

    @Test
    fun `toggleCell when cell activated cell should activate the corresponding cell`() {
        // Given
        val world = World(
            rowCount = 2,
            colCount = 3,
            generationCount = 2,
            cells = List(3) { List(3) { true } })
        given(repository.getStoredWorld()).willReturn(world)

        // When
        interactor.toggleCell(1, 1)

        // Then
        val newCells = world.copy().cells
                .map { it.toMutableList() }
                .apply { get(1)[1] = false }
        val expectedNewWorld = world.copy(cells = newCells)

        then(repository).should().storeWorld(expectedNewWorld)
        then(presenter).should().presentWorld(expectedNewWorld)
    }

    @Test
    fun `nextGeneration should increment generation number and present the new world`() {
        // Given
        val actualWorld: World = mock()
        val newWorld: World = mock()
        given(repository.getStoredWorld()).willReturn(actualWorld)
        given(nextGenerationCalculator.computeNextGeneration(actualWorld)).willReturn(newWorld)

        // When
        interactor.nextGeneration()

        // Then
        then(repository).should().storeWorld(newWorld)
        then(presenter).should().presentWorld(newWorld)
    }
}