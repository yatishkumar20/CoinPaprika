package com.yatish.coinpaprika.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatish.coinpaprika.domain.usecase.GetCoinDetailsUseCase
import com.yatish.coinpaprika.util.Constants.PARAM_COIN_ID
import com.yatish.coinpaprika.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val coinDetailsUseCase: GetCoinDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(PARAM_COIN_ID)?.let { coinId ->
            getCoinDetails(coinId)
        }
    }

    private fun getCoinDetails(coinId: String) {
        viewModelScope.launch {
            coinDetailsUseCase(coinId).collectLatest { result ->

                when (result) {
                    is Resource.Success -> {
                        _state.value = CoinDetailState(
                            coin = result.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = CoinDetailState(
                            error = result.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CoinDetailState(
                            isLoafing = true
                        )
                    }
                }

            }
        }
    }
}