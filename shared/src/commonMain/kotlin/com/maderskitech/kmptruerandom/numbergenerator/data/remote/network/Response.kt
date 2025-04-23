package com.maderskitech.kmptruerandom.numbergenerator.data.remote.network

import com.maderskitech.kmptruerandom.numbergenerator.data.common.ErrorType

sealed interface Response<out D, out E: ErrorType> {
    data class Success<out D>(val data: D): Response<D, Nothing>
    data class Failure<out E: ErrorType>(val error: E): Response<Nothing, E>
}