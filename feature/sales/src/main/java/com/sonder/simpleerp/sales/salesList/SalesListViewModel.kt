package com.sonder.simpleerp.sales.salesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.simpleerp.common.result.Result
import com.sonder.simpleerp.common.result.asResult
import com.sonder.simpleerp.data.repository.SalesRepository
import com.sonder.simpleerp.model.data.SaleWithValueResource
import com.sonder.simpleerp.sales.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesListViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    private val _salesState: MutableStateFlow<UiState<List<SaleWithValueResource>>> =
        MutableStateFlow(UiState.Initial)

    val salesState: StateFlow<UiState<List<SaleWithValueResource>>> = _salesState

    fun getSalesWithValue() {
        viewModelScope.launch {
            repository.getSalesWithValue().asResult().collect { salesResult ->
                _salesState.update {
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
