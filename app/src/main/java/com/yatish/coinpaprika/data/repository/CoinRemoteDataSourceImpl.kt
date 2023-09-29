package com.yatish.coinpaprika.data.repository

import com.yatish.coinpaprika.data.mapper.CoinDetailResponseMapper
import com.yatish.coinpaprika.data.mapper.CoinListResponseMapper
import com.yatish.coinpaprika.data.remote.CoinPaprikaApi
import com.yatish.coinpaprika.domain.error.ErrorHandler
import com.yatish.coinpaprika.domain.model.Coin
import com.yatish.coinpaprika.domain.model.CoinDetail
import com.yatish.coinpaprika.util.Constants.NOT_FOUND
import com.yatish.coinpaprika.util.Constants.NOT_FOUND_CODE
import com.yatish.coinpaprika.util.Constants.TOO_MANY_REQUEST
import com.yatish.coinpaprika.util.Constants.TOO_MANY_REQUEST_CODE
import com.yatish.coinpaprika.util.Constants.UNKNOWN
import com.yatish.coinpaprika.util.Constants.UNKNOWN_ERROR
import com.yatish.coinpaprika.util.ErrorEntity
import com.yatish.coinpaprika.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CoinRemoteDataSourceImpl @Inject constructor(
    private val coinPaprikaApi: CoinPaprikaApi,
    private val coinListResponseMapper: CoinListResponseMapper,
    private val coinDetailResponseMapper: CoinDetailResponseMapper,
    private val errorHandler: ErrorHandler
): CoinRemoteDataSource {
    override fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        try {
            val coinsResponse = coinPaprikaApi.getCoins()
            coinsResponse.body()?.let { response ->
                val coins = response.map { coinListResponseMapper.mapToDomainLayerModel(it) }
                emit(Resource.Success(coins))
            } ?: getErrorResult(coinsResponse).apply {
                emit(Resource.Error(message = this.first, errorEntity = this.second, data = null))
            }
        } catch (e: Exception) {
            getExceptionResult(e).apply {
                emit(Resource.Error(message = this.first, errorEntity = this.second, data = null))
            }
        }
    }

    private fun getExceptionResult(e: Exception) : Pair<String, ErrorEntity> {
        return Pair(e.localizedMessage ?: UNKNOWN_ERROR, errorHandler.getErrorDetails(e))
    }

    private fun <T> getErrorResult(response: Response<T>?): Pair<String, ErrorEntity> {
        return when (response?.raw()?.code) {
            NOT_FOUND_CODE -> Pair(
                NOT_FOUND,
                ErrorEntity.NotFound,
            )

            TOO_MANY_REQUEST_CODE -> Pair(
                TOO_MANY_REQUEST,
                ErrorEntity.TooManyRequests,
            )
            else -> Pair(
                UNKNOWN,
                ErrorEntity.Unknown,
            )
        }
    }


    override fun getCoinDetails(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            val coinDetailResponse = coinPaprikaApi.getCoinDetails(coinId = coinId)
            coinDetailResponse.body()?.let { response ->
                val coinDetails = coinDetailResponseMapper.mapToDomainLayerModel(response)
                emit(Resource.Success(coinDetails))
            } ?: getErrorResult(coinDetailResponse).apply {
                emit(Resource.Error(message = this.first, errorEntity = this.second, data = null))
            }

        } catch (e: Exception) {
            getExceptionResult(e).apply {
                emit(Resource.Error(message = this.first, errorEntity = this.second, data = null))
            }
        }
    }
}