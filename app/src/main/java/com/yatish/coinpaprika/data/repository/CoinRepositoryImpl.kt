package com.yatish.coinpaprika.data.repository

import com.yatish.coinpaprika.domain.model.Coin
import com.yatish.coinpaprika.domain.model.CoinDetail
import com.yatish.coinpaprika.domain.repository.CoinRepository
import com.yatish.coinpaprika.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoinRemoteDataSource
): CoinRepository {
    override fun getCoins(): Flow<Resource<List<Coin>>> {
        return remoteDataSource.getCoins()
    }

    override fun getCoinDetails(coinId: String): Flow<Resource<CoinDetail>> {
        return remoteDataSource.getCoinDetails(coinId)
    }

}