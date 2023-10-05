package com.yatish.coinpaprika.ui.coin_list

import com.yatish.domain.model.Coin

sealed class CoinListState {
    class Success(var data: List<Coin>) : CoinListState()
    class Error(var error: String) : CoinListState()
    class Loading(var isLoading: Boolean) : CoinListState()
}


