package com.yatish.coinpaprika.di

import com.google.gson.GsonBuilder
import com.yatish.coinpaprika.BuildConfig
import com.yatish.coinpaprika.data.remote.CoinPaprikaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    fun provideRetrofitClient(): CoinPaprikaApi = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(
            OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            ).build()
        )
        addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())
        )
    }.build().create(CoinPaprikaApi::class.java)
}