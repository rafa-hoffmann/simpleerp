package com.sonder.simpleerp.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesSerpDatabase(
        @ApplicationContext context: Context,
    ): SerpDatabase = Room.databaseBuilder(
        context,
        SerpDatabase::class.java,
        "serp-database",
    ).addMigrations(MIGRATION_1_2).build()
}
