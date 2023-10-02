package com.yatish.coinpaprika.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatish.coinpaprika.domain.usecase.GetCoinsUseCase
import com.yatish.coinpaprika.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinListUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch {
            coinListUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CoinListState(
                            coins = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _state.value = CoinListState(
                            error = result.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CoinListState(
                            isLoading = true
                        )
                    }
                }

            }
        }
    }
}