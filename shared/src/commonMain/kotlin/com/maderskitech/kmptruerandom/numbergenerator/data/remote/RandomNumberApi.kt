package com.maderskitech.kmptruerandom.numbergenerator.data.remote

import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.NetworkError
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.Response
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.encodeURLParameter

/**
 * Interface for the Random.org API.
 *
 * The service returns a plain-text integer followed by a newline.  We parse that into an [Int].
 */
interface RandomNumberApi {
    suspend fun getNumber(min: Int, max: Int): Response<Int?, NetworkError>
}

/**
 * Implementation of [RandomNumberApi] that calls https://www.random.org.
 *
 * The endpoint used is:
 *   https://www.random.org/integers/?num=1&min={min}&max={max}&col=1&base=10&format=plain&rnd=new
 *
 * The service returns a plain‑text integer followed by a newline.  We parse that into an [Int].
 */
class RandomNumberApiImpl(
    private val httpClient: HttpClient
) : RandomNumberApi {

    /*
     * Get a random number between [min] and [max].
     *
     * @param min The minimum value of the random number.
     * @param max The maximum value of the random number.
     * @return A response containing the random number or an error.
     */
    override suspend fun getNumber(min: Int, max: Int): Response<Int?, NetworkError> {
        // Build the URL with query parameters
        val url = buildString {
            append("https://www.random.org/integers/")
            append("?num=1")
            append("&min=${min.toString().encodeURLParameter()}")
            append("&max=${max.toString().encodeURLParameter()}")
            append("&col=1")
            append("&base=10")
            append("&format=plain")
            append("&rnd=new")
        }

        return try {
            val raw = httpClient.get(url).bodyAsText().trim()

            val number = raw.toIntOrNull()

            if (number != null) {
                Response.Success(number)
            } else {
                Response.Failure(
                    NetworkError.UNABLE_TO_PARSE_INTEGER
                )
            }
        } catch (e: Exception) {
            Response.Failure(
                NetworkError.UNKNOWN
            )
        }
    }
}