package com.yatish.feature.ui.coin_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yatish.feature.core.CoroutineRule
import com.yatish.feature.core.MockResponse.getMockCoins
import com.yatish.domain.usecase.GetCoinsUseCase
import com.yatish.common.util.ErrorEntity
import com.yatish.common.util.Resource
import com.yatish.cryptocurrency.mapper.CoinErrorMapper
import com.yatish.cryptocurrency.ui.CoinState
import com.yatish.cryptocurrency.ui.coin_list.CoinListViewModel
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
class CoinListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: CoinListViewModel
    private val coinsUseCase = mockk<GetCoinsUseCase>()
    private val coinErrorMapper = mockk<CoinErrorMapper>()

    @Test
    fun `GIVEN CoinUseCase WHEN invoked THEN should return coin list`() = runTest {
        coEvery { coinsUseCase.invoke() } returns flow { emit(Resource.Success(getMockCoins())) }
        viewModel = CoinListViewModel(
            coinsUseCase,
            coinErrorMapper
        )
        Assert.assertTrue(viewModel.state.value is CoinState.Success<*>)
    }

    @Test
    fun `GIVEN CoinUseCase WHEN invoked THEN should return error`() = runTest {
        coEvery { coinsUseCase.invoke() } returns flow { emit(Resource.Error(errorEntity = ErrorEntity.NetWork)) }
        coEvery { coinErrorMapper.mapToDomainLayer(ErrorEntity.NetWork) } returns NETWORK_ERROR
        viewModel = CoinListViewModel(
            coinsUseCase,
            coinErrorMapper
        )
        Assert.assertTrue(viewModel.state.value is CoinState.Error)
    }

    companion object {
        private const val NETWORK_ERROR = "Network Error"
    }
}