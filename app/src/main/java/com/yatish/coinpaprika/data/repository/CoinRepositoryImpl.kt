package com.yatish.coinpaprika.data.repository

import com.yatish.coinpaprika.domain.model.Coin
import com.yatish.coinpaprika.domain.model.CoinDetail
import com.yatish.coinpaprika.domain.repository.CoinRepository
import com.yatish.coinpaprika.util.ErrorEntity
import com.yatish.coinpaprika.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoinRemoteDataSource
) : CoinRepository {
    override fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())
        when (val result = remoteDataSource.getCoins()) {
            is Resource.Success -> {
                result.data?.let {
                    emit(
                        Resource.Success(it)
                    )
                } ?: emit(
                    Resource.Success(emptyList())
                )
            }
            is Resource.Error -> {
                emit(
                    Resource.Error(
                        errorEntity = result.errorEntity,
                        data = result.data
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

        emit(Resource.Loading())
        when (val result = remoteDataSource.getCoinDetails(coinId)) {
            is Resource.Success -> {
                result.data?.let {
                    emit(
                        Resource.Success(it)
                    )
                } ?: emit(
                    Resource.Error(
                        errorEntity = ErrorEntity.NotFound
                    )
                )
            }
            is Resource.Error -> {
                emit(
                    Resource.Error(
                        errorEntity = result.errorEntity,
                        data = result.data
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