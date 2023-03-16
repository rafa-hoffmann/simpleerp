package com.sonder.simpleerp.database

import androidx.room.RoomDatabase
import com.sonder.simpleerp.database.dao.SalesDao

abstract class SerpDatabase : RoomDatabase() {
    abstract fun salesDao(): SalesDao
}
