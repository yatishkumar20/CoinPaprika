package com.yatish.coinpaprika.domain.usecase

import com.yatish.coinpaprika.domain.model.Coin
import com.yatish.coinpaprika.domain.repository.CoinRepository
import com.yatish.coinpaprika.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = repository.getCoins()
}