package com.frederic.millard.android.kata.gameoflife.core

import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(DataProviderRunner::class)
class LivingCellAroundCalculatorTest {

    companion object {
        @DataProvider
        @JvmStatic
        @Suppress("unused")
        fun testData(): Array<Array<out Any?>> = arrayOf(
            arrayOf(generateWorld(emptyList()), 1 to 1, 0),

            arrayOf(generateWorld(listOf(0 to 0)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(0 to 1)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(0 to 2)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(1 to 0)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(1 to 2)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(2 to 0)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(2 to 1)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(2 to 2)), 1 to 1, 1),
            arrayOf(generateWorld(listOf(0 to 0, 0 to 1, 0 to 2, 1 to 0, 1 to 2, 2 to 0, 2 to 1, 2 to 2)), 1 to 1, 8),

            arrayOf(generateWorld(emptyList()), 0 to 0, 0),
            arrayOf(generateWorld(listOf(0 to 1)), 0 to 0, 1),
            arrayOf(generateWorld(listOf(1 to 0)), 0 to 0, 1),
            arrayOf(generateWorld(listOf(1 to 1)), 0 to 0, 1),
            arrayOf(generateWorld(listOf(0 to 1, 1 to 0, 1 to 1)), 0 to 0, 3),

            arrayOf(generateWorld(emptyList()), 2 to 2, 0),
            arrayOf(generateWorld(listOf(2 to 1)), 2 to 2, 1),
            arrayOf(generateWorld(listOf(1 to 1)), 2 to 2, 1),
            arrayOf(generateWorld(listOf(1 to 2)), 2 to 2, 1),
            arrayOf(generateWorld(listOf(2 to 1, 1 to 2, 1 to 1)), 2 to 2, 3),

            arrayOf(generateWorld(emptyList()), 2 to 0, 0),
            arrayOf(generateWorld(listOf(1 to 0)), 2 to 0, 1),
            arrayOf(generateWorld(listOf(1 to 1)), 2 to 0, 1),
            arrayOf(generateWorld(listOf(2 to 1)), 2 to 0, 1),
            arrayOf(generateWorld(listOf(1 to 0, 1 to 1, 2 to 1)), 2 to 0, 3),

            arrayOf(generateWorld(emptyList()), 0 to 2, 0),
            arrayOf(generateWorld(listOf(0 to 1)), 0 to 2, 1),
            arrayOf(generateWorld(listOf(1 to 1)), 0 to 2, 1),
            arrayOf(generateWorld(listOf(1 to 2)), 0 to 2, 1),
            arrayOf(generateWorld(listOf(0 to 1, 1 to 1, 1 to 2)), 0 to 2, 3)
        )

        private fun generateWorld(livingCells: List<Pair<Int, Int>>): World {
            return World(
                height = 3,
                width = 3,
                generationCount = 0,
                cells = List(3) { row -> List(3) { col -> livingCells.contains(row to col) } }
            )
        }
    }

    @Test
    @UseDataProvider("testData")
    fun `Test living cell around`(world: World, cell: Pair<Int, Int>, expectedCount: Int) {
        // Given
        val calculator = LivingCellAroundCalculator()

        // When
        val count = calculator.computeLivingCellsAround(world, cell.first, cell.second)

        // Then
        assertThat(count).isEqualTo(expectedCount)
    }

}