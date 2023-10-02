package com.yatish.coinpaprika.domain.model

import com.yatish.coinpaprika.data.remote.dto.Team

data class CoinDetail(
    val id: String,
    val name: String,
    val description: String,
    val symbol: String,
    val isActive: Boolean,
    val logo: String,
    val rank: Int,
    val isNew: Boolean,
    val tags: List<String>,
    val team: List<Team>,
)
