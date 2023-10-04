package com.yatish.coinpaprika.presentation.coin_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.yatish.coinpaprika.core.CoroutineRule
import com.yatish.coinpaprika.core.MockResponse
import com.yatish.coinpaprika.data.mapper.CoinErrorMapper
import com.yatish.coinpaprika.domain.usecase.GetCoinDetailsUseCase
import com.yatish.coinpaprika.domain.usecase.GetCoinsUseCase
import com.yatish.coinpaprika.presentation.coin_list.CoinListViewModel
import com.yatish.coinpaprika.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
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
    private val coinDetailUseCase = mockk<GetCoinDetailsUseCase>()
    private val coinErrorMapper = mockk<CoinErrorMapper>()

    @Before
    fun setUp() {
        val savedState = SavedStateHandle(mapOf("coinId" to "btc-bitcoin"))
        coEvery { coinDetailUseCase.invoke("btc-bitcoin") } returns flow {
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
    }

    @Test
    fun `GIVEN GetCoinDetailsUseCase WHEN invoked THEN should coin details`() = runTest {
        Assert.assertEquals("Bitcoin", viewModel.state.value.coin?.name)
    }
}