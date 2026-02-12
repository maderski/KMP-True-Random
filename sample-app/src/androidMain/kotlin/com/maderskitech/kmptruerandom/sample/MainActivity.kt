package com.maderskitech.kmptruerandom.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.RandomNumberApiImpl
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.HttpClientFactory
import com.maderskitech.kmptruerandom.numbergenerator.domain.DefaultRandomNumberRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    private lateinit var httpClient: HttpClient
    private lateinit var viewModel: RandomNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        httpClient = HttpClientFactory.create(OkHttp.create())
        val repository = DefaultRandomNumberRepository(RandomNumberApiImpl(httpClient))
        viewModel = RandomNumberViewModel(repository)

        setContent {
            RandomNumberApp(viewModel)
        }
    }

    override fun onDestroy() {
        viewModel.clear()
        httpClient.close()
        super.onDestroy()
    }
}
