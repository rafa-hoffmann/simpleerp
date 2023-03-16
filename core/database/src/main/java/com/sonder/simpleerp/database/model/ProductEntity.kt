package com.sonder.simpleerp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sonder.simpleerp.model.data.ProductResource

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val quantity: Int,
    val value: Float,
    val saleId: Long
)

fun List<ProductEntity>.asExternalModel() = map {
    ProductResource(
        id = it.id,
        name = it.name,
        quantity = it.quantity,
        value = it.value,
        saleId = it.saleId
    )
}
