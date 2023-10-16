package com.yatish.cryptocurrency.ui

sealed class CoinState {
    class Success<T: Any>(val data: T) : CoinState()
    class Error(val error: String) : CoinState()
    class Loading(val loading: Boolean) : CoinState()
}
