package com.maderskitech.kmptruerandom.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maderskitech.kmptruerandom.numbergenerator.data.RandomNumberRepository
import kotlinx.coroutines.launch

@Composable
fun RandomNumberApp(repository: RandomNumberRepository) {
    var randomNumberText by remember { mutableStateOf("Press the button") }
    val scope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = randomNumberText)
            Button(
                onClick = {
                    scope.launch {
                        val value = repository.getRandomNumber(min = 1, max = 100)
                        randomNumberText = value.toString()
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Get random number")
            }
        }
    }
}
