package com.maderskitech.kmptruerandom.platform

import com.maderskitech.kmptruerandom.numbergenerator.platform.NetworkClient
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

/**
 * Android implementation of the NetworkClient interface.
 * This class provides the necessary functionality to make network requests on Android platforms
 */
class AndroidNetworkClient : NetworkClient {
    override val httpClient: HttpClient = HttpClientFactory.create(OkHttp.create())
}
