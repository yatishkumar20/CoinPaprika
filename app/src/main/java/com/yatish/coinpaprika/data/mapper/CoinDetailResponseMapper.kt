package com.yatish.coinpaprika.data.mapper

import com.yatish.coinpaprika.data.remote.dto.CoinDetailDto
import com.yatish.coinpaprika.domain.model.CoinDetail
import javax.inject.Inject

class CoinDetailResponseMapper @Inject constructor(): Mapper<CoinDetail, CoinDetailDto> {
    override fun mapToDomainLayerModel(input: CoinDetailDto): CoinDetail {
        return CoinDetail(
            id = input.id,
            name = input.name,
            description = input.description,
            symbol = input.symbol,
            isActive = input.isActive,
            logo = input.logo,
            rank = input.rank,
            isNew = input.isNew,
            tags = input.tags.map { it.name },
            team = input.team
        )
    }
}