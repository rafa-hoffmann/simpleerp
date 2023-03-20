package com.sonder.simpleerp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sonder.simpleerp.database.dao.SalesDao
import com.sonder.simpleerp.database.model.ProductEntity
import com.sonder.simpleerp.database.model.SaleEntity

@Database(
    entities = [
        SaleEntity::class,
        ProductEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class SerpDatabase : RoomDatabase() {
    abstract fun salesDao(): SalesDao
}
