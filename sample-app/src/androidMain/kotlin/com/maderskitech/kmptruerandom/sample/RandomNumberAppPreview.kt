package com.maderskitech.kmptruerandom.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun RandomNumberAppPreview() {
    RandomNumberApp(
        uiState = RandomNumberUiState(randomNumberText = "42"),
        onGetRandomNumberClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun RandomNumberAppPreviewLoading() {
    RandomNumberApp(
        uiState = RandomNumberUiState(isLoading = true),
        onGetRandomNumberClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun RandomNumberAppPreviewError() {
    RandomNumberApp(
        uiState = RandomNumberUiState(errorMessage = "Failed to get random number"),
        onGetRandomNumberClick = {},
    )
}
