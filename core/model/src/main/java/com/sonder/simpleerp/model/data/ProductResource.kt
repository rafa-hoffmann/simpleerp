package com.sonder.simpleerp.model.data

data class ProductResource(
    val id: Long? = null,
    val name: String,
    val quantity: Float,
    val value: Float,
    val saleId: Long,
    var discount: Float? = null
)
