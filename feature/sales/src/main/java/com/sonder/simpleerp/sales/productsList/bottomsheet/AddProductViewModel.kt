package com.sonder.simpleerp.sales.productsList.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.simpleerp.common.result.Result
import com.sonder.simpleerp.common.result.asResult
import com.sonder.simpleerp.data.repository.SalesRepository
import com.sonder.simpleerp.model.data.ProductResource
import com.sonder.simpleerp.sales.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    private val _addProductState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Initial)

    val addProductState: StateFlow<UiState<Unit>> = _addProductState

    fun addProduct(productResource: ProductResource) {
        viewModelScope.launch {
            repository.insertProduct(productResource).asResult().collect { addProductResult ->
                _addProductState.update {
                    when (addProductResult) {
                        is Result.Success -> UiState.Success(addProductResult.data)
                        Result.Loading -> UiState.Loading
                        is Result.Error -> UiState.Error(addProductResult.exception)
                    }
                }
            }
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _addProductState.value = UiState.Initial
        }
    }
}
