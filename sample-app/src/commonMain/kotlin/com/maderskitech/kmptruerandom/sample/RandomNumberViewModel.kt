package com.maderskitech.kmptruerandom.sample

import com.maderskitech.kmptruerandom.numbergenerator.data.RandomNumberRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RandomNumberUiState(
    val randomNumberText: String = "Press the button",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class RandomNumberViewModel(
    private val repository: RandomNumberRepository,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
) {
    private val _uiState = MutableStateFlow(RandomNumberUiState())
    val uiState: StateFlow<RandomNumberUiState> = _uiState.asStateFlow()

    fun loadRandomNumber() {
        if (_uiState.value.isLoading) return

        scope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching { repository.getRandomNumber(min = 1, max = 100) }
                .onSuccess { value ->
                    _uiState.update {
                        it.copy(
                            randomNumberText = value.toString(),
                            isLoading = false,
                            errorMessage = null,
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to get random number",
                        )
                    }
                }
        }
    }

    fun clear() {
        scope.cancel()
    }
}
