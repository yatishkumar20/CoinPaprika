package com.yatish.data.repository

import com.yatish.common.util.Resource
import com.yatish.domain.model.Coin
import com.yatish.domain.model.CoinDetail

interface CoinRemoteDataSource {
    suspend fun getCoins(): Resource<List<Coin>>

    suspend fun getCoinDetails(coinId: String): Resource<CoinDetail>
}