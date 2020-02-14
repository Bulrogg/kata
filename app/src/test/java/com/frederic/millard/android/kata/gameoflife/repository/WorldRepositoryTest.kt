package com.frederic.millard.android.kata.gameoflife.repository

import com.frederic.millard.android.kata.gameoflife.core.World
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WorldRepositoryTest {

    @Test
    fun `repository get stored world`() {
        // Given
        val repository = WorldRepository()
        val world: World = mock()

        // When
        repository.storeWorld(world)
        val storedWorld = repository.getStoredWorld()

        // Then
        assertThat(storedWorld).isEqualTo(world)
    }

}