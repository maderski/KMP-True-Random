package com.maderskitech.kmptruerandom.numbergenerator.data.remote.network

import com.maderskitech.kmptruerandom.numbergenerator.data.common.ErrorType

enum class NetworkError : ErrorType {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    FORBIDDEN,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNABLE_TO_PARSE_INTEGER,
    UNKNOWN;
}