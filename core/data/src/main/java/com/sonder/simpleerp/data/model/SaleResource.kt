package com.sonder.simpleerp.data.model

import com.sonder.simpleerp.database.model.SaleEntity
import com.sonder.simpleerp.model.data.SaleResource

internal fun SaleResource.asEntity() = SaleEntity(id = id, clientName = clientName)
