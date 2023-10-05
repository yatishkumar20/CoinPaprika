package com.yatish.domain.usecase

import com.yatish.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String) = repository.getCoinDetails(coinId)
}