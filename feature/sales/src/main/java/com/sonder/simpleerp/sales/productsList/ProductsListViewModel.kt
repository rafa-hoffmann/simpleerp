package com.sonder.simpleerp.sales.productsList

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
class ProductsListViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    private val _productsState: MutableStateFlow<UiState<List<ProductResource>>> =
        MutableStateFlow(UiState.Initial)

    val productsState: StateFlow<UiState<List<ProductResource>>> = _productsState

    fun getProducts(saleId: Long) {
        viewModelScope.launch {
            repository.getProducts(saleId).asResult().collect { salesResult ->
                _productsState.update {
                    when (salesResult) {
                        is Result.Success -> UiState.Success(salesResult.data)
                        Result.Loading -> UiState.Loading
                        is Result.Error -> UiState.Error(salesResult.exception)
                    }
                }
            }
        }
    }
}
