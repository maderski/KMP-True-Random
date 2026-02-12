package com.maderskitech.kmptruerandom.numbergenerator.domain

import com.maderskitech.kmptruerandom.numbergenerator.data.RandomNumberRepository
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.RandomNumberApi
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.Response
import kotlin.random.Random

/**
 * Default implementation of the RandomNumberRepository interface.
 * Uses the Random.org API to generate random numbers.
 * @param api The RandomNumberApi instance to use for making API calls.
 */
class DefaultRandomNumberRepository(
    private val api: RandomNumberApi
) : RandomNumberRepository {
    /*
     * Generates a random number between min and max.
     *
     * @param min The minimum value of the random number.
     * @param max The maximum value of the random number.
     * @return The generated random number.
     */
    override suspend fun getRandomNumber(min: Int, max: Int): Int {
        require(min <= max) { "min must be less than or equal to max" }

        return when (val response = api.getNumber(min, max)) {
            is Response.Success -> response.data ?: generateLocalRandom(min, max)
            is Response.Failure -> generateLocalRandom(min, max)
        }
    }

    private fun generateLocalRandom(min: Int, max: Int): Int {
        val rangeSize = max.toLong() - min.toLong() + 1L
        val offset = Random.nextLong(until = rangeSize)
        return (min.toLong() + offset).toInt()
    }
}
