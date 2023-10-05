package com.yatish.coinpaprika.ui.coin_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.yatish.coinpaprika.core.CoroutineRule
import com.yatish.coinpaprika.core.MockResponse
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
internal class CoinDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: CoinDetailsViewModel
    private val coinDetailUseCase = mockk<com.yatish.domain.usecase.GetCoinDetailsUseCase>()
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
        Assert.assertEquals(BITCOIN, viewModel.state.value.coin?.name)
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
        Assert.assertEquals(NETWORK_ERROR, viewModel.state.value.error)
    }

    companion object {
        private const val COIN_ID_KEY = "coinId"
        private const val COIN_ID_VALUE = "btc-bitcoin"
        private const val BITCOIN = "Bitcoin"
        private const val NETWORK_ERROR = "Network Error"
    }
}