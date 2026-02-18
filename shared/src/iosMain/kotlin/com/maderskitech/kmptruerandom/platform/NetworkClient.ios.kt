package com.maderskitech.kmptruerandom.platform

import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.HttpClientFactory
import com.maderskitech.kmptruerandom.numbergenerator.platform.NetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

/**
 * iOS implementation of NetworkClient.
 * This class provides iOS-specific functionality for network operations.
 */
class IOSNetworkClient : NetworkClient {
    override val httpClient: HttpClient = HttpClientFactory.create(Darwin.create())
}
