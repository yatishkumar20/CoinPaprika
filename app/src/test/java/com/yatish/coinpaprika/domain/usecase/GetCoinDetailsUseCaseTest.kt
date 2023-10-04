package com.yatish.coinpaprika.domain.usecase

import com.yatish.coinpaprika.core.MockResponse
import com.yatish.coinpaprika.domain.repository.CoinRepository
import com.yatish.coinpaprika.util.ErrorEntity
import com.yatish.coinpaprika.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


internal class GetCoinDetailsUseCaseTest {

    @MockK
    private val coinRepository = mockk<CoinRepository>(relaxed = true)

    private lateinit var getCoinDetailsUseCase: GetCoinDetailsUseCase

    @Before
    fun setUp() {
        getCoinDetailsUseCase = GetCoinDetailsUseCase(
            coinRepository
        )
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN called THEN should return coin details`() = runTest {
        coEvery { coinRepository.getCoinDetails("btc-bitcoin") } returns flow { emit(Resource.Success(MockResponse.getMockCoinDetails()))}
        getCoinDetailsUseCase("btc-bitcoin").collectLatest {
            when(it) {
                is Resource.Success -> {
                    Assert.assertEquals("Bitcoin", it.data?.name)
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN called THEN should return error`() = runTest {
        coEvery { coinRepository.getCoinDetails("btc-bitcoin") } returns flow { emit(Resource.Error(errorEntity = ErrorEntity.NotFound))}
        getCoinDetailsUseCase("btc-bitcoin").collectLatest {
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