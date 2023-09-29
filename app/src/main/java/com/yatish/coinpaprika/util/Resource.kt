package com.yatish.coinpaprika.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorEntity: ErrorEntity? = null
) {
    class Success<T>(data: T) : Resource<T>()
    class Error<T>(message: String, data: T? = null, errorEntity: ErrorEntity? = null) :
        Resource<T>()

    class Loading<T>(data: T?) : Resource<T>()
}