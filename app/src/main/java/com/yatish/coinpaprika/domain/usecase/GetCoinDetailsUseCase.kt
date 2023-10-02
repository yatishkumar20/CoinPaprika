package com.yatish.coinpaprika.domain.usecase

import com.yatish.coinpaprika.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String) = repository.getCoinDetails(coinId)
}