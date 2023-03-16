package com.sonder.simpleerp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sonder.simpleerp.model.data.SaleResource

@Entity(tableName = "sales")
data class SaleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val clientName: String
)

fun SaleEntity.asExternalModel() = SaleResource(id = id, clientName = clientName)
