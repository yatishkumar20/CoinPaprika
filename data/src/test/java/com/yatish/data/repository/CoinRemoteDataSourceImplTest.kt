package com.yatish.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yatish.common.util.Resource
import com.yatish.data.core.MockResponse
import com.yatish.data.core.MockResponse.ERROR_429
import com.yatish.data.error.ErrorHandlerImpl
import com.yatish.data.mapper.CoinDetailResponseMapper
import com.yatish.data.mapper.CoinListResponseMapper
import com.yatish.data.remote.CoinPaprikaApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import retrofit2.Response


@ExperimentalCoroutinesApi
class CoinRemoteDataSourceImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var apiService: CoinPaprikaApi

    private lateinit var coinRemoteDataSource: CoinRemoteDataSource

    private val dispatcher = Dispatchers.IO
    private val listResponseMapper = CoinListResponseMapper()
    private val detailsResponseMapper = CoinDetailResponseMapper()
    private val errorHandler = ErrorHandlerImpl()

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coinRemoteDataSource = CoinRemoteDataSourceImpl(
            dispatcher = dispatcher,
            coinPaprikaApi = apiService,
            coinListResponseMapper = listResponseMapper,
            coinDetailResponseMapper = detailsResponseMapper,
            errorHandler = errorHandler
        )
    }

    @Test
    fun `GIVEN GetCoins WHEN called THEN should return list of coins`() = runTest {
        coEvery { apiService.getCoins() } returns Response.success(MockResponse.getMockCoinDto())
        val coins = coinRemoteDataSource.getCoins()
        Assert.assertTrue(coins is Resource.Success)
    }

    @Test
    fun `GIVEN GetCoins WHEN called THEN should throws exception`() = runTest {
        coEvery { apiService.getCoins() } throws Exception("Exception")
        val coins = coinRemoteDataSource.getCoins()
        Assert.assertTrue(coins is Resource.Error)
    }

    @Test
    fun `GIVEN GetCoins WHEN called THEN should return error`() = runTest {
        coEvery { apiService.getCoins() } returns Response.error(429, ERROR_429.toResponseBody())
        val coins = coinRemoteDataSource.getCoins()
        Assert.assertTrue(coins is Resource.Error)
    }

    @Test
    fun `GIVEN GetCoinDetails WHEN called THEN should return coin details`() = runTest {
        coEvery { apiService.getCoinDetails(BITCOIN_ID) } returns Response.success(MockResponse.getMockCoinDetailsDto())
        val coinDetail = coinRemoteDataSource.getCoinDetails(BITCOIN_ID)
        Assert.assertTrue(coinDetail is Resource.Success)
    }

    @Test
    fun `GIVEN GetCoinDetails WHEN called THEN should return error`() = runTest {
        coEvery { apiService.getCoinDetails(BITCOIN_ID) } returns Response.error(429, ERROR_429.toResponseBody())
        val coinDetail = coinRemoteDataSource.getCoinDetails(BITCOIN_ID)
        Assert.assertTrue(coinDetail is Resource.Error)
    }

    @Test
    fun `GIVEN GetCoinDetails WHEN called THEN should throws exception`() = runTest {
        coEvery { apiService.getCoinDetails(BITCOIN_ID) } throws Exception("Exception")
        val coinDetail = coinRemoteDataSource.getCoinDetails(BITCOIN_ID)
        Assert.assertTrue(coinDetail is Resource.Error)
    }

    companion object {
        private const val BITCOIN_ID = "btc-bitcoin"
    }
}