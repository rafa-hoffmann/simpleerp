package com.sonder.simpleerp.data.repository

import com.sonder.simpleerp.common.dispatchers.Dispatcher
import com.sonder.simpleerp.common.dispatchers.SerpDispatchers
import com.sonder.simpleerp.data.model.asEntity
import com.sonder.simpleerp.database.dao.SalesDao
import com.sonder.simpleerp.database.model.asExternalModel
import com.sonder.simpleerp.model.data.ProductResource
import com.sonder.simpleerp.model.data.SaleResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SalesRepositoryImpl @Inject constructor(
    @Dispatcher(SerpDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val salesDao: SalesDao
) : SalesRepository {
    override fun getSalesWithProducts() = flow {
        val resourceMap: MutableMap<SaleResource, List<ProductResource>> = mutableMapOf()

        salesDao.getSalesWithProducts().map {
            resourceMap.put(it.key.asExternalModel(), it.value.asExternalModel())
        }

        emit(resourceMap)
    }.flowOn(ioDispatcher)

    override fun getSalesWithValue() = flow {
        emit(salesDao.getSalesWithValue().asExternalModel())
    }.flowOn(ioDispatcher)

    override fun insertSale(saleResource: SaleResource) = flow {
        emit(salesDao.insertSale(saleResource.asEntity()))
    }.flowOn(ioDispatcher)

    override fun insertProduct(productResource: ProductResource) = flow {
        emit(salesDao.insertProduct(productResource.asEntity()))
    }.flowOn(ioDispatcher)
}
