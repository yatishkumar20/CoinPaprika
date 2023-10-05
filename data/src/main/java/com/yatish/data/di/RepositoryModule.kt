package com.yatish.data.di

import com.yatish.data.error.ErrorHandlerImpl
import com.yatish.data.repository.CoinRemoteDataSource
import com.yatish.data.repository.CoinRemoteDataSourceImpl
import com.yatish.data.repository.CoinRepositoryImpl
import com.yatish.domain.error.ErrorHandler
import com.yatish.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoinRepository(
        coinRepositoryImpl: CoinRepositoryImpl
    ): CoinRepository

    @Binds
    @Singleton
    abstract fun bindCoinRemoteDataSource(
        coinRemoteDataSourceImpl: CoinRemoteDataSourceImpl
    ): CoinRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindErrorHandler(
        errorHandlerImpl: ErrorHandlerImpl
    ): ErrorHandler
}