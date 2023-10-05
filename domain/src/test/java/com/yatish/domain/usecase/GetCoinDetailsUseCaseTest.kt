package com.yatish.domain.usecase

import com.yatish.domain.core.MockResponse
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
        coEvery { coinRepository.getCoinDetails(BITCOIN_ID) } returns flow { emit(Resource.Success(
            MockResponse.getMockCoinDetails()))}
        getCoinDetailsUseCase(BITCOIN_ID).collectLatest {
            when(it) {
                is Resource.Success -> {
                    Assert.assertEquals(BITCOIN, it.data?.name)
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN called THEN should return error`() = runTest {
        coEvery { coinRepository.getCoinDetails(BITCOIN_ID) } returns flow { emit(Resource.Error(errorEntity = ErrorEntity.NotFound))}
        getCoinDetailsUseCase(BITCOIN_ID).collectLatest {
            when(it) {
                is Resource.Error -> {
                    Assert.assertEquals(ErrorEntity.NotFound, it.errorEntity)
                }
                else -> {

                }
            }
        }
    }

    companion object {
        private const val BITCOIN_ID = "btc-bitcoin"
        private const val BITCOIN = "Bitcoin"
    }
}