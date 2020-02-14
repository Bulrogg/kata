package com.frederic.millard.android.kata.gameoflife.presentation

import com.frederic.millard.android.kata.gameoflife.core.World
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameOfLifePresenterImplTest {

    @Mock private lateinit var display: GameOfLifeDisplay

    @InjectMocks private lateinit var presenter: GameOfLifePresenterImpl

    @Test
    fun `presentWorld when display the world`() {
        // Given
        val world: World = mock()

        // When
        presenter.presentWorld(world)

        // Then
        display.displayWorld(world)
    }

}