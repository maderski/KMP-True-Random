package com.maderskitech.kmptruerandom.numbergenerator.domain

import com.maderskitech.kmptruerandom.numbergenerator.data.RandomNumberRepository
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.RandomNumberApi
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.NetworkError
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class DefaultRandomNumberRepositoryTest {
    private val mockApi = mockk<RandomNumberApi>()
    private val repository = DefaultRandomNumberRepository(mockApi)

    @Test
    fun `getRandomNumber should return the number from API when success`() = runBlocking {
        // Arrange
        val min = 1
        val max = 100
        val expectedNumber = 42
        coEvery { mockApi.getNumber(min, max) } returns Response.Success(expectedNumber)

        // Act
        val result = repository.getRandomNumber(min, max)

        // Assert
        assertEquals(expectedNumber, result)
        coVerify { mockApi.getNumber(min, max) }
    }

    @Test
    fun `getRandomNumber should fallback to local random when API fails`() = runBlocking {
        // Arrange
        val min = 1
        val max = 100
        coEvery { mockApi.getNumber(min, max) } returns Response.Failure(NetworkError.NO_INTERNET)

        // Act
        val result = repository.getRandomNumber(min, max)

        // Assert
        assertTrue(result in min..max)
        coVerify { mockApi.getNumber(min, max) }
    }

    @Test
    fun `getRandomNumber should throw when min is greater than max`() = runBlocking {
        assertFailsWith<IllegalArgumentException> {
            repository.getRandomNumber(min = 10, max = 1)
        }
    }
}
