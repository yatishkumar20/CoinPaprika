package com.yatish.data.mapper

import com.yatish.data.remote.dto.CoinDto
import com.yatish.domain.mapper.Mapper
import com.yatish.domain.model.Coin
import javax.inject.Inject

class CoinListResponseMapper @Inject constructor(): Mapper<Coin, CoinDto> {
    override fun mapToDomainLayer(response: CoinDto): Coin {
        return with(response) {
            Coin(
                id = this.id,
                isActive = this.isActive,
                name = this.name,
                rank = this.rank,
                symbol = this.symbol
            )
        }
    }
}