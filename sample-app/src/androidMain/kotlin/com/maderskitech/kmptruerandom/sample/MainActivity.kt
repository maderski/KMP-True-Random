package com.maderskitech.kmptruerandom.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.RandomNumberApiImpl
import com.maderskitech.kmptruerandom.numbergenerator.domain.DefaultRandomNumberRepository
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.HttpClientFactory
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val httpClient = HttpClientFactory.create(OkHttp.create())
        val repository = DefaultRandomNumberRepository(RandomNumberApiImpl(httpClient))

        setContent {
            RandomNumberApp(repository)
        }
    }
}
