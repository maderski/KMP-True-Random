package com.maderskitech.kmptruerandom.sample

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.RandomNumberApiImpl
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.HttpClientFactory
import com.maderskitech.kmptruerandom.numbergenerator.domain.DefaultRandomNumberRepository
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController {
    val viewModel = remember {
        val httpClient = HttpClientFactory.create(Darwin.create())
        val repository = DefaultRandomNumberRepository(RandomNumberApiImpl(httpClient))
        RandomNumberViewModel(repository)
    }

    val uiState by viewModel.uiState.collectAsState()
    RandomNumberApp(
        uiState = uiState,
        onGetRandomNumberClick = viewModel::loadRandomNumber,
    )
}
