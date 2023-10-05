package com.yatish.data.mapper

import com.yatish.data.remote.dto.CoinDto
import com.yatish.domain.model.Coin
import javax.inject.Inject

class CoinListResponseMapper @Inject constructor(): Mapper<Coin, CoinDto> {
    override fun mapToDomainLayer(input: CoinDto): Coin {
        return Coin(
            id = input.id,
            isActive = input.isActive,
            name = input.name,
            rank = input.rank,
            symbol = input.symbol
        )
    }
}