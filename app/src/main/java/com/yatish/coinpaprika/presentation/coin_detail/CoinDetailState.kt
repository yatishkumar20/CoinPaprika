package com.yatish.coinpaprika.presentation.coin_detail

import com.yatish.coinpaprika.domain.model.CoinDetail

data class CoinDetailState(
    val isLoafing: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String? = ""
)
