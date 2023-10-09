package com.yatish.domain.usecase

import com.yatish.domain.model.Coin
import com.yatish.domain.repository.CoinRepository
import com.yatish.common.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = repository.getCoins()
}