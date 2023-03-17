package com.sonder.simpleerp.database.model

import androidx.room.Embedded
import com.sonder.simpleerp.model.data.SaleWithValueResource

data class SaleWithValueEntity(
    @Embedded val sale: SaleEntity,
    val value: Float
)

fun List<SaleWithValueEntity>.asExternalModel() = map {
    SaleWithValueResource(sale = it.sale.asExternalModel(), value = it.value)
}
