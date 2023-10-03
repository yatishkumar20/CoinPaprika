package com.yatish.coinpaprika.data.error

import com.yatish.coinpaprika.domain.error.ErrorHandler
import com.yatish.coinpaprika.util.ErrorEntity
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor(): ErrorHandler {
    override fun getErrorDetails(throwable: Throwable): ErrorEntity {
        return when(throwable) {
            is IOException -> ErrorEntity.NetWork
            is HttpException -> {
                when(throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                    else -> ErrorEntity.NetWork
                }
            }
            else -> ErrorEntity.Unknown

        }
    }
}