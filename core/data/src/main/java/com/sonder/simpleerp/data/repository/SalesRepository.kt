package com.sonder.simpleerp.data.repository

import com.sonder.simpleerp.model.data.ProductResource
import com.sonder.simpleerp.model.data.SaleResource
import kotlinx.coroutines.flow.Flow

interface SalesRepository {
    fun getSalesWithProducts(): Flow<Map<SaleResource, List<ProductResource>>>

    fun insertSale(saleResource: SaleResource): Flow<Int>

    fun insertProduct(productResource: ProductResource): Flow<Int>
}
