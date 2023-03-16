package com.sonder.simpleerp.sales.salesList.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.simpleerp.common.result.Result
import com.sonder.simpleerp.common.result.asResult
import com.sonder.simpleerp.data.repository.SalesRepository
import com.sonder.simpleerp.model.data.SaleResource
import com.sonder.simpleerp.sales.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSaleViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    private val _addSaleState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Initial)

    val addSaleState: StateFlow<UiState<Unit>> = _addSaleState

    fun addSale(sale: SaleResource) {
        viewModelScope.launch {
            repository.insertSale(sale).asResult().collect { addSaleResult ->
                _addSaleState.update {
                    when (addSaleResult) {
                        is Result.Success -> UiState.Success(addSaleResult.data)
                        Result.Loading -> UiState.Loading
                        is Result.Error -> UiState.Error(addSaleResult.exception)
                    }
                }
            }
        }
    }
}
