package com.sonder.simpleerp.data.model

import com.sonder.simpleerp.database.model.ProductEntity
import com.sonder.simpleerp.model.data.ProductResource

internal fun ProductResource.asEntity() = ProductEntity(
    id = id,
    name = name,
    quantity = quantity,
    value = value,
    saleId = saleId
)
