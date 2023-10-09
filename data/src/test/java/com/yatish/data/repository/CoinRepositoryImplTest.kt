package com.yatish.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yatish.common.util.ErrorEntity
import com.yatish.common.util.Resource
import com.yatish.data.core.MockResponse.getMockCoin
import com.yatish.data.core.MockResponse.getMockCoinDetails
import com.yatish.domain.repository.CoinRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoinRepositoryImplTest {
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
        coEvery { remoteDataSource.getCoins() } returns Resource.Success(getMockCoin())
        coinRepository.getCoins().filter { it is Resource.Success }.collectLatest { result ->
            Assert.assertTrue(result is Resource.Success)
        }
    }

    @Test
    fun `GIVEN CoinRepository WHEN called getCoins THEN should return error`() = runTest {
        coEvery { remoteDataSource.getCoins() } returns Resource.Error(errorEntity = ErrorEntity.Unknown)
        coinRepository.getCoins().filter { it is Resource.Error }.collectLatest { result ->
            Assert.assertTrue(result is Resource.Error)
        }
    }

    @Test
    fun `GIVEN CoinRepository WHEN called getCoinDetails THEN should return coin details`() =
        runTest {
            coEvery { remoteDataSource.getCoinDetails(BITCOIN_ID) } returns Resource.Success(
                getMockCoinDetails()
            )
            coinRepository.getCoinDetails(BITCOIN_ID).filter { it is Resource.Success }.collectLatest { result ->
                Assert.assertTrue(result is Resource.Success)
            }
        }

    @Test
    fun `GIVEN CoinRepository WHEN called getCoinDetails THEN should return error`() = runTest {
        coEvery { remoteDataSource.getCoinDetails(BITCOIN_ID) } returns Resource.Error(errorEntity = ErrorEntity.Unknown)
        coinRepository.getCoinDetails(BITCOIN_ID).filter { it is Resource.Error }.collectLatest { result ->
            Assert.assertTrue(result is Resource.Error)
        }
    }

    companion object {
        private const val BITCOIN_ID = "btc-bitcoin"
    }

}