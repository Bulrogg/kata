package com.frederic.millard.android.kata.gameoflife

import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractorImpl
import com.frederic.millard.android.kata.gameoflife.core.GameOfLifePresenter
import com.frederic.millard.android.kata.gameoflife.core.NextGenerationCalculator
import com.frederic.millard.android.kata.gameoflife.core.World
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
class GameOfLifeInteractorImplTest {

    @Mock private lateinit var presenter: GameOfLifePresenter
    @Mock private lateinit var repository: WorldRepository
    @Mock private lateinit var nextGenerationCalculator: NextGenerationCalculator

    @InjectMocks private lateinit var interactor: GameOfLifeInteractorImpl

    @Test
    fun `initWorld should present an empty world`() {
        // Given
        val width = 2
        val height = 3

        // When
        interactor.initWorld(height, width)

        // Then
        val world = World(
            height = 2,
            width = 3,
            generationCount = 0,
            cells = List(2) { List(3) { false } })
        then(repository).should().storeWorld(world)
        then(presenter).should().presentWorld(world)
    }

    @Test
    fun `activateCell should activate the corresponding cell`() {
        // Given
        val world = World(
            height = 2,
            width = 3,
            generationCount = 2,
            cells = List(3) { List(3) { false } })
        given(repository.getStoredWorld()).willReturn(world)

        // When
        interactor.activateCell(1, 1)

        // Then
        val newWorld = world.copy().cells
                .map { it.toMutableList() }
                .apply { get(1)[1] = true }

        then(repository).should().storeWorld(world.copy(cells = newWorld))
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