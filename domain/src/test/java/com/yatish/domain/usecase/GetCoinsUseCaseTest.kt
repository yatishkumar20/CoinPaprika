package com.yatish.domain.usecase

import com.yatish.domain.core.MockResponse.getMockCoins
import com.yatish.domain.model.Coin
import com.yatish.domain.repository.CoinRepository
import com.yatish.common.util.ErrorEntity
import com.yatish.common.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinsUseCaseTest {

    @MockK
    private val coinRepository = mockk<CoinRepository>()

    private lateinit var getCoinUseCase: GetCoinsUseCase

    @Before
    fun setUp() {
        getCoinUseCase = GetCoinsUseCase(
            coinRepository
        )
    }

    @Test
    fun `GIVEN GetCoinsUseCase WHEN called THEN should return list of coins`() = runTest {
        val response: Flow<Resource<List<Coin>>> = flow {
            emit(Resource.Success(getMockCoins()))
        }
        coEvery { coinRepository.getCoins() } returns response
        getCoinUseCase.invoke().collectLatest {
            Assert.assertTrue(it is Resource.Success)
        }
    }

    @Test
    fun `GIVEN GetCoinsUseCase WHEN called THEN should return error`() = runTest {
        val response: Flow<Resource<List<Coin>>> = flow {
            emit(Resource.Error(errorEntity = ErrorEntity.NotFound))
        }
        coEvery { coinRepository.getCoins() } returns response
        getCoinUseCase.invoke().collectLatest {
            Assert.assertTrue(it is Resource.Error)
        }
    }
}