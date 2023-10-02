package com.yatish.coinpaprika.data.repository

import com.yatish.coinpaprika.data.mapper.CoinDetailResponseMapper
import com.yatish.coinpaprika.data.mapper.CoinListResponseMapper
import com.yatish.coinpaprika.data.remote.CoinPaprikaApi
import com.yatish.coinpaprika.di.IoDispatcher
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
                    val coins = response.map { coinListResponseMapper.mapToDomainLayerModel(it) }
                    return@withContext Resource.Success(data = coins)
                } ?: run {
                    val errorResult = getErrorResult(coinsResponse)
                    return@withContext Resource.Error(
                        message = errorResult.first,
                        errorEntity = errorResult.second,
                        data = null
                    )
                }
            } catch (e: Exception) {
                val exceptionResult = getExceptionResult(e)
                return@withContext Resource.Error(
                    message = exceptionResult.first,
                    errorEntity = exceptionResult.second,
                    data = null
                )
            }
        }

    private fun getExceptionResult(e: Exception): Pair<String, ErrorEntity> {
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


    override suspend fun getCoinDetails(coinId: String): Resource<CoinDetail> =
        withContext(dispatcher) {
            try {
                val coinDetailResponse = coinPaprikaApi.getCoinDetails(coinId = coinId)
                coinDetailResponse.body()?.let { response ->
                    val coinDetails = coinDetailResponseMapper.mapToDomainLayerModel(response)
                    return@withContext Resource.Success(coinDetails)
                } ?: run {
                    val errorResult = getErrorResult(coinDetailResponse)
                    return@withContext Resource.Error(
                        message = errorResult.first,
                        errorEntity = errorResult.second,
                        data = null
                    )
                }
            } catch (e: Exception) {
                val exceptionResult = getExceptionResult(e)
                return@withContext Resource.Error(
                    message = exceptionResult.first,
                    errorEntity = exceptionResult.second,
                    data = null
                )
            }
        }
}