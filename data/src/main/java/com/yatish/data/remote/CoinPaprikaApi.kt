package com.yatish.data.remote

import com.yatish.data.remote.dto.CoinDetailDto
import com.yatish.data.remote.dto.CoinDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins(): Response<List<CoinDto>>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinDetails(@Path("coinId") coinId: String): Response<CoinDetailDto>

}