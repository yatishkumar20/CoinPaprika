package com.yatish.data.repository

import com.yatish.common.util.ErrorEntity
import com.yatish.common.util.Resource
import com.yatish.domain.model.Coin
import com.yatish.domain.model.CoinDetail
import com.yatish.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoinRemoteDataSource
) : CoinRepository {
    override fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        when (val result = remoteDataSource.getCoins()) {
            is Resource.Success -> {
                emit(
                    Resource.Success(result.data)
                )
            }
            is Resource.Error -> {
                emit(
                    Resource.Error(
                        errorEntity = result.errorEntity
                    )
                )
            }
            else -> {
                emit(
                    Resource.Error(
                        errorEntity = ErrorEntity.Unknown
                    )
                )
            }
        }
    }

    override fun getCoinDetails(coinId: String): Flow<Resource<CoinDetail>> = flow {
        when (val result = remoteDataSource.getCoinDetails(coinId)) {
            is Resource.Success -> {
                emit(
                    Resource.Success(result.data)
                )
            }
            is Resource.Error -> {
                emit(
                    Resource.Error(
                        errorEntity = result.errorEntity
                    )
                )
            }
            else -> {
                emit(
                    Resource.Error(
                        errorEntity = ErrorEntity.Unknown
                    )
                )
            }
        }
    }

}