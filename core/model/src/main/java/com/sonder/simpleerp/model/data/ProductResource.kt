package com.sonder.simpleerp.model.data

data class ProductResource(
    val id: Long,
    val name: String,
    val quantity: Int,
    val value: Float,
    val saleId: Long
)
