package com.yatish.domain.error

import com.yatish.common.util.ErrorEntity

interface ErrorHandler {
    fun getErrorDetails(throwable: Throwable): ErrorEntity
}