package com.yatish.domain.usecase

import com.yatish.common.util.Resource
import com.yatish.domain.model.CoinDetail
import com.yatish.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> =
        repository.getCoinDetails(coinId)
}