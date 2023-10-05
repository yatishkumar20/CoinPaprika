package com.yatish.coinpaprika.ui.coin_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yatish.coinpaprika.core.CoroutineRule
import com.yatish.coinpaprika.core.MockResponse.getMockCoins
import com.yatish.coinpaprika.mapper.CoinErrorMapper
import com.yatish.domain.util.ErrorEntity
import com.yatish.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
internal class CoinListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: CoinListViewModel
    private val coinsUseCase = mockk<com.yatish.domain.usecase.GetCoinsUseCase>()
    private val coinErrorMapper = mockk<CoinErrorMapper>()

    @Test
    fun `GIVEN CoinUseCase WHEN invoked THEN should return coin list`() = runTest {
        coEvery { coinsUseCase.invoke() } returns flow { emit(Resource.Success(getMockCoins())) }
        viewModel = CoinListViewModel(
            coinsUseCase,
            coinErrorMapper
        )
        Assert.assertEquals(1, viewModel.state.value.coins.size)
    }

    @Test
    fun `GIVEN CoinUseCase WHEN invoked THEN should return error`() = runTest {
        coEvery { coinsUseCase.invoke() } returns flow { emit(Resource.Error(errorEntity = ErrorEntity.NetWork)) }
        coEvery { coinErrorMapper.mapToDomainLayer(ErrorEntity.NetWork) } returns NETWORK_ERROR
        viewModel = CoinListViewModel(
            coinsUseCase,
            coinErrorMapper
        )
        Assert.assertEquals(NETWORK_ERROR, viewModel.state.value.error)
    }

    @Test
    fun `GIVEN CoinUseCase WHEN invoked THEN should return loading state`() = runTest {
        coEvery { coinsUseCase.invoke() } returns flow { emit(Resource.Loading()) }
        viewModel = CoinListViewModel(
            coinsUseCase,
            coinErrorMapper
        )
        Assert.assertTrue(viewModel.state.value.isLoading)
    }

    companion object {
        private const val NETWORK_ERROR = "Network Error"
    }
}