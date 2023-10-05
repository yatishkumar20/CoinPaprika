package com.yatish.domain.usecase

import com.yatish.domain.core.MockResponse.getMockCoins
import com.yatish.domain.repository.CoinRepository
import com.yatish.domain.util.ErrorEntity
import com.yatish.domain.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetCoinsUseCaseTest {

    @MockK
    private val coinRepository = mockk<CoinRepository>(relaxed = true)

    private lateinit var getCoinUseCase: GetCoinsUseCase

    @Before
    fun setUp() {
        getCoinUseCase = GetCoinsUseCase(
            coinRepository
        )
    }

    @Test
    fun `GIVEN GetCoinsUseCase WHEN called THEN should return list of coins`() = runTest {
        coEvery { coinRepository.getCoins() } returns flow { emit(Resource.Success(getMockCoins()))}
        getCoinUseCase().collectLatest {
            when(it) {
                is Resource.Success -> {
                    Assert.assertEquals(1, it.data?.size)
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `GIVEN GetCoinsUseCase WHEN called THEN should return error`() = runTest {
        coEvery { coinRepository.getCoins() } returns flow { emit(Resource.Error(errorEntity = ErrorEntity.NotFound))}
        getCoinUseCase().collectLatest {
            when(it) {
                is Resource.Error -> {
                    Assert.assertEquals(ErrorEntity.NotFound, it.errorEntity)
                }
                else -> {

                }
            }
        }
    }
}