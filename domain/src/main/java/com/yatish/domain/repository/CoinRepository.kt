package com.yatish.domain.repository

import com.yatish.domain.model.Coin
import com.yatish.domain.model.CoinDetail
import com.yatish.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoins(): Flow<Resource<List<Coin>>>

    fun getCoinDetails(coinId: String): Flow<Resource<CoinDetail>>
}