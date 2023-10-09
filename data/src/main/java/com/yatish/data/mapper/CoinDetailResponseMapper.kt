package com.yatish.data.mapper

import com.yatish.data.remote.dto.CoinDetailDto
import com.yatish.domain.mapper.Mapper
import com.yatish.domain.model.CoinDetail
import com.yatish.domain.model.TeamMember
import javax.inject.Inject

class CoinDetailResponseMapper @Inject constructor(): Mapper<CoinDetail, CoinDetailDto> {
    override fun mapToDomainLayer(response: CoinDetailDto): CoinDetail {
        return with(response){
            CoinDetail(
                id = this.id,
                name = this.name,
                description = this.description,
                symbol = this.symbol,
                isActive = this.isActive,
                logo = this.logo,
                rank = this.rank,
                isNew = this.isNew,
                tags = this.tags.map { it.name },
                team = this.team.map { TeamMember(it.name, it.position) }
            )
        }
    }
}