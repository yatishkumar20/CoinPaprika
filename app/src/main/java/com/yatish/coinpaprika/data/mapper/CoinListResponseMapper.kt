package com.yatish.coinpaprika.data.mapper

import com.yatish.coinpaprika.data.remote.dto.CoinDto
import com.yatish.coinpaprika.domain.model.Coin
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