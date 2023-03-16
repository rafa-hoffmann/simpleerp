package com.sonder.simpleerp.data.di

import com.sonder.simpleerp.data.repository.SalesRepository
import com.sonder.simpleerp.data.repository.SalesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindSalesRepository(
        salesRepository: SalesRepositoryImpl
    ): SalesRepository
}
