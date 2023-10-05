package com.yatish.coinpaprika.ui.coin_detail

import com.yatish.domain.model.CoinDetail

sealed class CoinDetailState {
    class Success(var coin: CoinDetail) : CoinDetailState()
    class Error(var error: String) : CoinDetailState()
    class Loading(var isLoading: Boolean) : CoinDetailState()
}
