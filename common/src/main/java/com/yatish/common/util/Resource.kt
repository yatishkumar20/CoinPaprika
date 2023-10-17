package com.yatish.common.util

sealed class Resource<T : Any> {
    class Success<T: Any>(val data: T) : Resource<T>()
    class Error<T: Any>(val errorEntity: ErrorEntity? = null) :
        Resource<T>()
}