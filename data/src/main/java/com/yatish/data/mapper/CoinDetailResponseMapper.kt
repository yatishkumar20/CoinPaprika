package com.yatish.data.mapper

import com.yatish.data.remote.dto.CoinDetailDto
import com.yatish.domain.model.CoinDetail
import com.yatish.domain.model.TeamMember
import javax.inject.Inject

class CoinDetailResponseMapper @Inject constructor(): Mapper<CoinDetail, CoinDetailDto> {
    override fun mapToDomainLayer(input: CoinDetailDto): CoinDetail {
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
            team = input.team.map { TeamMember(it.name, it.position) }
        )
    }
}