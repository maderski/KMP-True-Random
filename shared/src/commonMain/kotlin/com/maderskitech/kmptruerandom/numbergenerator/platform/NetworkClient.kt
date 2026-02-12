package com.maderskitech.kmptruerandom.numbergenerator.platform

import io.ktor.client.HttpClient

/**
 * NetworkClient interface for providing the httpclient for making network requests.
 *
 * This interface is used to provide a common interface for different network clients,
 * such as Darwin for iOS or OkHttp for Android..
 **/
interface NetworkClient {
    val httpClient: HttpClient
}