package com.yatish.domain.error

import com.yatish.domain.util.ErrorEntity

interface ErrorHandler {
    fun getErrorDetails(throwable: Throwable): ErrorEntity
}