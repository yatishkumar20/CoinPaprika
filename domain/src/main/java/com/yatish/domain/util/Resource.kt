package com.yatish.domain.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorEntity: ErrorEntity? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String? = null, data: T? = null, errorEntity: ErrorEntity? = null) :
        Resource<T>(data, message, errorEntity)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}