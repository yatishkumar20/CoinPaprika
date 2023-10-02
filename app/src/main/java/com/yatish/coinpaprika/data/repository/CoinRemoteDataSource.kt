package com.yatish.coinpaprika.data.repository

import com.yatish.coinpaprika.domain.model.Coin
import com.yatish.coinpaprika.domain.model.CoinDetail
import com.yatish.coinpaprika.util.Resource

interface CoinRemoteDataSource {
    suspend fun getCoins(): Resource<List<Coin>>

    suspend fun getCoinDetails(coinId: String): Resource<CoinDetail>
}