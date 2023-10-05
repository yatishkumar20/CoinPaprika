package com.yatish.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yatish.data.core.MockResponse.getMockCoinDetails
import com.yatish.data.core.MockResponse.getMockCoins
import com.yatish.domain.repository.CoinRepository
import com.yatish.domain.util.ErrorEntity
import com.yatish.domain.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class CoinRepositoryImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private val remoteDataSource = mockk<CoinRemoteDataSource>(relaxed = true)

    private lateinit var coinRepository: CoinRepository

    @Before
    fun setUp() {
        coinRepository = CoinRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `GIVEN CoinRepository WHEN called getCoins THEN should return list of coins`() = runTest {
        coEvery { remoteDataSource.getCoins() } returns Resource.Success(getMockCoins())
        coinRepository.getCoins().collectLatest { result ->
            when(result) {
                is Resource.Success -> {
                    Assert.assertEquals(1, result.data?.size)
                }
                else -> {}
            }
        }
    }

    @Test
    fun `GIVEN CoinRepository WHEN called getCoins THEN should return error`() = runTest {
        coEvery { remoteDataSource.getCoins() } returns Resource.Error(errorEntity = ErrorEntity.Unknown)
        coinRepository.getCoins().collectLatest { result ->
            when(result) {
                is Resource.Error -> {
                    Assert.assertEquals(ErrorEntity.Unknown, result.errorEntity)
                }
                else -> {}
            }
        }
    }

    @Test
    fun `GIVEN CoinRepository WHEN called getCoinDetails THEN should return coin details`() = runTest {
        coEvery { remoteDataSource.getCoinDetails(BITCOIN_ID) } returns Resource.Success(
            getMockCoinDetails()
        )
        coinRepository.getCoinDetails(BITCOIN_ID).collectLatest { result ->
            when(result) {
                is Resource.Success -> {
                    Assert.assertEquals(BITCOIN, result.data?.name)
                }
                else -> {}
            }
        }
    }

    @Test
    fun `GIVEN CoinRepository WHEN called getCoinDetails THEN should return error`() = runTest {
        coEvery { remoteDataSource.getCoinDetails(BITCOIN_ID) } returns Resource.Error(errorEntity = ErrorEntity.Unknown)
        coinRepository.getCoinDetails(BITCOIN_ID).collectLatest { result ->
            when(result) {
                is Resource.Error -> {
                    Assert.assertEquals(ErrorEntity.Unknown, result.errorEntity)
                }
                else -> {}
            }
        }
    }

    companion object {
        private const val BITCOIN_ID = "btc-bitcoin"
        private const val BITCOIN = "Bitcoin"
    }

}