package com.sonder.simpleerp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sonder.simpleerp.database.model.ProductEntity
import com.sonder.simpleerp.database.model.SaleEntity
import com.sonder.simpleerp.database.model.SaleWithValueEntity

@Dao
interface SalesDao {
    @Query(
        "SELECT * FROM sales JOIN products ON sales.id = products.saleId"
    )
    fun getSalesWithProducts(): Map<SaleEntity, List<ProductEntity>>

    @Query(
        "SELECT SUM(products.value * products.quantity) FROM products"
    )
    fun getTotalSalesValue(): Float

    @Query(
        "SELECT sales.*, SUM(products.value * products.quantity) as value FROM sales LEFT JOIN products ON (products.saleId = sales.id)\n" +
            "GROUP BY sales.id"
    )
    fun getSalesWithValue(): List<SaleWithValueEntity>

    @Query("SELECT * FROM products WHERE products.saleId = :saleId")
    fun getProductsBySale(saleId: Long): List<ProductEntity>

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity)

    @Insert
    suspend fun insertSale(saleEntity: SaleEntity)
}
