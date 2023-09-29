package com.yatish.coinpaprika.domain.error

import com.yatish.coinpaprika.util.ErrorEntity

interface ErrorHandler {
    fun getErrorDetails(throwable: Throwable): ErrorEntity
}