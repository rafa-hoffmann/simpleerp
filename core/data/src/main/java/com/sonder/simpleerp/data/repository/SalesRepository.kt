package com.sonder.simpleerp.data.repository

import com.sonder.simpleerp.model.data.ProductResource
import com.sonder.simpleerp.model.data.SaleResource
import com.sonder.simpleerp.model.data.SaleWithValueResource
import kotlinx.coroutines.flow.Flow

interface SalesRepository {
    fun getSalesWithProducts(): Flow<Map<SaleResource, List<ProductResource>>>

    fun getSalesWithValue(): Flow<List<SaleWithValueResource>>

    fun getProducts(saleId: Long): Flow<List<ProductResource>>

    fun insertSale(saleResource: SaleResource): Flow<Unit>

    fun insertProduct(productResource: ProductResource): Flow<Unit>
}
