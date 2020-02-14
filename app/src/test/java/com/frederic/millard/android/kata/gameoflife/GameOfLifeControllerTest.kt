package com.frederic.millard.android.kata.gameoflife

import com.frederic.millard.android.kata.gameoflife.controller.GameOfLifeController
import com.frederic.millard.android.kata.gameoflife.core.GameOfLifeInteractor
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.then
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameOfLifeControllerTest {

    @Mock private lateinit var interactor: GameOfLifeInteractor

    @InjectMocks private lateinit var controller: GameOfLifeController

    @Test
    fun `initWorld should call interactor`() {
        // Given
        val height = 13
        val width = 12

        // When
        controller.initWorld(height, width)

        // Then
        then(interactor).should(only()).initWorld(height, width)
    }

    @Test
    fun `activateCell should call interactor`() {
        // Given
        val y = 2
        val x = 3

        // When
        controller.activateCell(x, y)

        // Then
        then(interactor).should(only()).activateCell(x, y)
    }

}