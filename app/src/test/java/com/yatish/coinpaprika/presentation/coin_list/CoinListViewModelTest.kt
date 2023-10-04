package com.yatish.coinpaprika.presentation.coin_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yatish.coinpaprika.core.CoroutineRule
import com.yatish.coinpaprika.core.MockResponse.getMockCoins
import com.yatish.coinpaprika.data.mapper.CoinErrorMapper
import com.yatish.coinpaprika.domain.usecase.GetCoinsUseCase
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
internal class CoinListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: CoinListViewModel
    private val coinsUseCase = mockk<GetCoinsUseCase>()
    private val coinErrorMapper = mockk<CoinErrorMapper>()

    @Before
    fun setUp() {
        coEvery { coinsUseCase.invoke() } returns flow { emit(Resource.Success(getMockCoins())) }
        viewModel = CoinListViewModel(
            coinsUseCase,
            coinErrorMapper
        )
    }

    @Test
    fun `GIVEN CoinUseCase WHEN invoked THEN should return coin list`() = runTest {
        Assert.assertEquals(1, viewModel.state.value.coins.size)
    }
}