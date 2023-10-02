package com.yatish.coinpaprika.presentation.coin_list

import com.yatish.coinpaprika.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String? = ""
)
