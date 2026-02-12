package com.maderskitech.kmptruerandom.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RandomNumberApp(viewModel: RandomNumberViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val errorMessage = uiState.errorMessage

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = uiState.randomNumberText)

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }

            Button(
                onClick = viewModel::loadRandomNumber,
                enabled = !uiState.isLoading,
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text(if (uiState.isLoading) "Loading..." else "Get random number")
            }
        }
    }
}
