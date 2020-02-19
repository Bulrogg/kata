package com.frederic.millard.android.kata.gameoflife.core

import com.nhaarman.mockitokotlin2.given
import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@RunWith(DataProviderRunner::class)
class NextGenerationCalculatorTest {

    @Mock private lateinit var livingCellAroundCalculator: LivingCellAroundCalculator

    @InjectMocks private lateinit var calculator: NextGenerationCalculator

    companion object {
        @DataProvider
        @JvmStatic
        @Suppress("unused")
        fun testData(): Array<Array<out Any?>> = arrayOf(
            arrayOf(TestData(livingCell = true, livingCellAround = 0, expectedResult = false)),
            arrayOf(TestData(livingCell = true, livingCellAround = 1, expectedResult = false)),
            arrayOf(TestData(livingCell = true, livingCellAround = 2, expectedResult = true)),
            arrayOf(TestData(livingCell = true, livingCellAround = 3, expectedResult = true)),
            arrayOf(TestData(livingCell = true, livingCellAround = 4, expectedResult = false)),
            arrayOf(TestData(livingCell = true, livingCellAround = 5, expectedResult = false)),
            arrayOf(TestData(livingCell = true, livingCellAround = 6, expectedResult = false)),
            arrayOf(TestData(livingCell = true, livingCellAround = 7, expectedResult = false)),
            arrayOf(TestData(livingCell = true, livingCellAround = 8, expectedResult = false)),

            arrayOf(TestData(livingCell = false, livingCellAround = 0, expectedResult = false)),
            arrayOf(TestData(livingCell = false, livingCellAround = 1, expectedResult = false)),
            arrayOf(TestData(livingCell = false, livingCellAround = 2, expectedResult = false)),
            arrayOf(TestData(livingCell = false, livingCellAround = 3, expectedResult = true)),
            arrayOf(TestData(livingCell = false, livingCellAround = 4, expectedResult = false)),
            arrayOf(TestData(livingCell = false, livingCellAround = 5, expectedResult = false)),
            arrayOf(TestData(livingCell = false, livingCellAround = 6, expectedResult = false)),
            arrayOf(TestData(livingCell = false, livingCellAround = 7, expectedResult = false)),
            arrayOf(TestData(livingCell = false, livingCellAround = 8, expectedResult = false))
        )
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `computeNextGeneration should increment generation number`() {
        // Given
        val world = World(
            height = 0,
            width = 0,
            generationCount = 2,
            cells = emptyList()
        )

        // When
        val newWorld = calculator.computeNextGeneration(world)

        // Then
        assertThat(newWorld.generationCount).isEqualTo(3)
    }

    @Test
    @UseDataProvider("testData")
    fun `computeNextGeneration test data`(testData: TestData) {
        // Given
        val world = World(
            height = 1,
            width = 1,
            generationCount = 0,
            cells = List(1) { List(1) { testData.livingCell } }
        )
        given(livingCellAroundCalculator.computeLivingCellsAround(world, 0, 0)).willReturn(testData.livingCellAround)

        // When
        val newWorld = calculator.computeNextGeneration(world)

        // Then
        assertThat(newWorld.cells[0][0]).isEqualTo(testData.expectedResult)
    }

}

data class TestData(val livingCell: Boolean, val livingCellAround: Int, val expectedResult: Boolean)