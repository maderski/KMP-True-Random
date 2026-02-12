package com.maderskitech.kmptruerandom.numbergenerator.data.remote

import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.NetworkError
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.Response
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RandomNumberApiTest {

    private val testUrl = "https://www.random.org/integers/"

    @Test
    fun `getNumber returns success when response contains valid integer`() = runBlocking {
        // Given
        val api = RandomNumberApiImpl(
            HttpClient(MockEngine { request ->
                respond(
                    body = "42\n",
                    status = HttpStatusCode.OK,
                    headers = Headers.build {
                        append("Content-Type", "text/plain")
                    }
                )
            })
        )

        // When
        val result = api.getNumber(min = 1, max = 100)

        // Then
        assertTrue(result is Response.Success)
        assertEquals(42, result.data)
    }

    @Test
    fun `getNumber returns failure when response cannot be parsed as integer`() = runBlocking {
        // Given
        val api = RandomNumberApiImpl(
            HttpClient(MockEngine { request ->
                respond(
                    body = "invalid\n",
                    status = HttpStatusCode.OK,
                    headers = Headers.build {
                        append("Content-Type", "text/plain")
                    }
                )
            })
        )

        // When
        val result = api.getNumber(min = 1, max = 100)

        // Then
        assertTrue(result is Response.Failure)
        assertEquals(NetworkError.UNABLE_TO_PARSE_INTEGER, result.error)
    }

    @Test
    fun `getNumber returns failure when request fails`() = runBlocking {
        // Given
        val api = RandomNumberApiImpl(
            HttpClient(MockEngine { request ->
                respond(
                    status = HttpStatusCode.InternalServerError,
                    headers = Headers.build {
                        append("Content-Type", "text/plain")
                    }
                )
            })
        )

        // When
        val result = api.getNumber(min = 1, max = 100)

        // Then
        assertTrue(result is Response.Failure)
        assertEquals(NetworkError.UNKNOWN, result.error)
    }

    @Test
    fun `getNumber constructs correct URL with query parameters`() = runBlocking {
        // Given
        val mockEngine = MockEngine { request ->
            // Verify the URL construction by checking the request URL
            assertEquals(
                "$testUrl?num=1&min=1&max=100&col=1&base=10&format=plain&rnd=new",
                request.url.toString()
            )
            respond(
                body = "42\n",
                status = HttpStatusCode.OK,
                headers = Headers.build {
                    append("Content-Type", "text/plain")
                }
            )
        }
        val api = RandomNumberApiImpl(HttpClient(mockEngine))

        // When
        val result = api.getNumber(min = 1, max = 100)

        // Then
        assertTrue(result is Response.Success)
        assertEquals(42, result.data)
    }
}
