package com.sonder.simpleerp.sales

sealed interface UiState<out T> {
    data class Success<T>(val value: T) : UiState<T>
    data class Error(val exception: Throwable? = null) : UiState<Nothing>
    object Loading : UiState<Nothing>
    object Initial : UiState<Nothing>
}
