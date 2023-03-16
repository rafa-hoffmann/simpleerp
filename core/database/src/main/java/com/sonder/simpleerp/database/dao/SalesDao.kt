package com.sonder.simpleerp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sonder.simpleerp.database.model.ProductEntity
import com.sonder.simpleerp.database.model.SaleEntity

@Dao
interface SalesDao {
    @Query(
        "SELECT * FROM sales" +
            "JOIN products ON sales.id = product.saleId"
    )
    fun getSalesWithProducts(): Map<SaleEntity, List<ProductEntity>>

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity): Int

    @Insert
    suspend fun insertSale(saleEntity: SaleEntity): Int
}
