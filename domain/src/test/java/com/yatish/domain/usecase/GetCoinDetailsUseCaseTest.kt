package com.yatish.domain.usecase

import com.yatish.domain.core.MockResponse
import com.yatish.domain.model.CoinDetail
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
class GetCoinDetailsUseCaseTest {

    @MockK
    private val coinRepository = mockk<CoinRepository>()

    private lateinit var getCoinDetailsUseCase: GetCoinDetailsUseCase

    @Before
    fun setUp() {
        getCoinDetailsUseCase = GetCoinDetailsUseCase(
            coinRepository
        )
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN called THEN should return coin details`() = runTest {
        val response: Flow<Resource.Success<CoinDetail>> = flow {
            emit(
                Resource.Success(
                    MockResponse.getMockCoinDetails()
                )
            )
        }
        coEvery { coinRepository.getCoinDetails(BITCOIN_ID) } returns response
        getCoinDetailsUseCase(BITCOIN_ID).collectLatest {
            Assert.assertTrue(it is Resource.Success)
        }
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN called THEN should return error`() = runTest {
        val response: Flow<Resource.Error<CoinDetail>> = flow {
            emit(Resource.Error(errorEntity = ErrorEntity.NotFound))
        }
        coEvery { coinRepository.getCoinDetails(BITCOIN_ID) } returns response
        getCoinDetailsUseCase(BITCOIN_ID).collectLatest {
            Assert.assertTrue(it is Resource.Error)
        }
    }

    companion object {
        private const val BITCOIN_ID = "btc-bitcoin"
    }
}