package com.yatish.cryptocurrency.ui

sealed class CoinState<T: Any> {
    class Success<T: Any>(val data: T) : CoinState<T>()
    class Error<T: Any>(val error: String) : CoinState<T>()
    class Loading<T: Any>(val loading: Boolean) : CoinState<T>()
}
