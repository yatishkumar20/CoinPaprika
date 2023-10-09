package com.yatish.feature.ui.coin_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.yatish.feature.core.CoroutineRule
import com.yatish.feature.core.MockResponse
import com.yatish.domain.usecase.GetCoinDetailsUseCase
import com.yatish.common.util.ErrorEntity
import com.yatish.common.util.Resource
import com.yatish.cryptocurrency.mapper.CoinErrorMapper
import com.yatish.cryptocurrency.ui.CoinState
import com.yatish.cryptocurrency.ui.coin_detail.CoinDetailsViewModel
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
class CoinDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: CoinDetailsViewModel
    private val coinDetailUseCase = mockk<GetCoinDetailsUseCase>()
    private val coinErrorMapper = mockk<CoinErrorMapper>()

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN invoked THEN should coin details`() = runTest {
        val savedState = SavedStateHandle(mapOf(COIN_ID_KEY to COIN_ID_VALUE))
        coEvery { coinDetailUseCase.invoke(COIN_ID_VALUE) } returns flow {
            emit(
                Resource.Success(
                    MockResponse.getMockCoinDetails()
                )
            )
        }
        viewModel = CoinDetailsViewModel(
            coinDetailUseCase,
            coinErrorMapper,
            savedState
        )
        Assert.assertTrue(viewModel.state.value is CoinState.Success<*>)
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN invoked THEN should return error`() = runTest {
        val savedState = SavedStateHandle(mapOf(COIN_ID_KEY to COIN_ID_VALUE))
        coEvery { coinDetailUseCase.invoke(COIN_ID_VALUE) } returns flow {
            emit(
                Resource.Error(
                    errorEntity = ErrorEntity.NetWork
                )
            )
        }
        coEvery { coinErrorMapper.mapToDomainLayer(ErrorEntity.NetWork) } returns NETWORK_ERROR

        viewModel = CoinDetailsViewModel(
            coinDetailUseCase,
            coinErrorMapper,
            savedState
        )
        Assert.assertTrue(viewModel.state.value is CoinState.Error)
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN invoked THEN should return loading state`() = runTest {
        val savedState = SavedStateHandle(mapOf(COIN_ID_KEY to COIN_ID_VALUE))
        coEvery { coinDetailUseCase.invoke(COIN_ID_VALUE) } returns flow {
            emit(
                Resource.Loading()
            )
        }

        viewModel = CoinDetailsViewModel(
            coinDetailUseCase,
            coinErrorMapper,
            savedState
        )
        Assert.assertTrue(viewModel.state.value is CoinState.Loading)
    }

    companion object {
        private const val COIN_ID_KEY = "coinId"
        private const val COIN_ID_VALUE = "btc-bitcoin"
        private const val NETWORK_ERROR = "Network Error"
    }
}