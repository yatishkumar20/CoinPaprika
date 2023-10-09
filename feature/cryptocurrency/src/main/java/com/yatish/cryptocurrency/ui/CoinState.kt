package com.yatish.cryptocurrency.ui

sealed class CoinState {
    class Success<T: Any>(var data: T) : CoinState()
    class Error(var error: String) : CoinState()
    class Loading(var loading: Boolean) : CoinState()
}
