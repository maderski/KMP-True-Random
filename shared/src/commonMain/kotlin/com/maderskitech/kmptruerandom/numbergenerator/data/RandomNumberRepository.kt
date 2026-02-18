package com.maderskitech.kmptruerandom.numbergenerator.data

interface RandomNumberRepository {
    suspend fun getRandomNumber(min: Int, max: Int): Int
}