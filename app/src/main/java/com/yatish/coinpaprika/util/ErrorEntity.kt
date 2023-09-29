package com.yatish.coinpaprika.util

sealed class ErrorEntity {
    object NetWork: ErrorEntity()

    object NotFound : ErrorEntity()

    object TooManyRequests : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()
}