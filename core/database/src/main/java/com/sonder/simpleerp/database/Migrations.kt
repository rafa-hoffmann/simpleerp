package com.sonder.simpleerp.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE products_new (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, value REAL NOT NULL,saleId INTEGER NOT NULL);"
        )
        database.execSQL(
            "INSERT INTO products_new (id, name, quantity, value, saleId) SELECT id, name, quantity, value, saleId FROM products"
        )
        database.execSQL("DROP TABLE products")
        database.execSQL("ALTER TABLE products_new RENAME TO products")
    }
}