package com.maderskitech.kmptruerandom.numbergenerator.data.remote

import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.NetworkError
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.Response

interface RandomNumberApi {
    suspend fun getNumber(min: Int, max: Int): Response<Int?, NetworkError>
}