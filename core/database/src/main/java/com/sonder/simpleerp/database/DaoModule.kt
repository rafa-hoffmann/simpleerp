package com.sonder.simpleerp.database

import com.sonder.simpleerp.database.dao.SalesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesSalesDao(
        database: SerpDatabase,
    ): SalesDao = database.salesDao()
}
