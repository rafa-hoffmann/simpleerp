package com.sonder.simpleerp.sales

import java.text.NumberFormat
import java.util.Currency

fun Number.toMonetaryUnit(): String = NumberFormat.getCurrencyInstance().let {
    it.maximumFractionDigits = 0
    it.currency = Currency.getInstance("BRL")

    it.format(this)
}
