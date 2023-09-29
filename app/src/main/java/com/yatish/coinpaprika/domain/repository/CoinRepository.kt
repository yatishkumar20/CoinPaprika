package com.yatish.coinpaprika.domain.repository

import com.yatish.coinpaprika.domain.model.Coin
import com.yatish.coinpaprika.domain.model.CoinDetail
import com.yatish.coinpaprika.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoins(): Flow<Resource<List<Coin>>>

    fun getCoinDetails(coinId: String): Flow<Resource<CoinDetail>>
}