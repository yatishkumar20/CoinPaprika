package com.yatish.coinpaprika.data.repository

import com.yatish.coinpaprika.data.mapper.CoinDetailResponseMapper
import com.yatish.coinpaprika.data.mapper.CoinListResponseMapper
import com.yatish.coinpaprika.data.remote.CoinPaprikaApi
import com.yatish.coinpaprika.di.IoDispatcher
import com.yatish.coinpaprika.domain.error.ErrorHandler
import com.yatish.coinpaprika.domain.model.Coin
import com.yatish.coinpaprika.domain.model.CoinDetail
import com.yatish.coinpaprika.util.Constants.NOT_FOUND_CODE
import com.yatish.coinpaprika.util.Constants.TOO_MANY_REQUEST_CODE
import com.yatish.coinpaprika.util.ErrorEntity
import com.yatish.coinpaprika.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CoinRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val coinPaprikaApi: CoinPaprikaApi,
    private val coinListResponseMapper: CoinListResponseMapper,
    private val coinDetailResponseMapper: CoinDetailResponseMapper,
    private val errorHandler: ErrorHandler
) : CoinRemoteDataSource {
    override suspend fun getCoins(): Resource<List<Coin>> =
        withContext(dispatcher) {
            try {
                val coinsResponse = coinPaprikaApi.getCoins()
                coinsResponse.body()?.let { response ->
                    val coins = response.map { coinListResponseMapper.mapToDomainLayer(it) }
                    return@withContext Resource.Success(data = coins)
                } ?: run {
                    return@withContext Resource.Error(
                        errorEntity = getErrorResult(coinsResponse)
                    )
                }
            } catch (e: Exception) {
                return@withContext Resource.Error(
                    errorEntity = getExceptionResult(e)
                )
            }
        }

    private fun getExceptionResult(e: Exception): ErrorEntity {
        return errorHandler.getErrorDetails(e)
    }

    private fun <T> getErrorResult(response: Response<T>?): ErrorEntity {
        return when (response?.raw()?.code) {
            NOT_FOUND_CODE -> ErrorEntity.NotFound
            TOO_MANY_REQUEST_CODE -> ErrorEntity.TooManyRequests
            else -> ErrorEntity.Unknown
        }
    }


    override suspend fun getCoinDetails(coinId: String): Resource<CoinDetail> =
        withContext(dispatcher) {
            try {
                val coinDetailResponse = coinPaprikaApi.getCoinDetails(coinId = coinId)
                coinDetailResponse.body()?.let { response ->
                    val coinDetails = coinDetailResponseMapper.mapToDomainLayer(response)
                    return@withContext Resource.Success(coinDetails)
                } ?: run {
                    return@withContext Resource.Error(
                        errorEntity = getErrorResult(coinDetailResponse)
                    )
                }
            } catch (e: Exception) {
                return@withContext Resource.Error(
                    errorEntity = getExceptionResult(e)
                )
            }
        }
}