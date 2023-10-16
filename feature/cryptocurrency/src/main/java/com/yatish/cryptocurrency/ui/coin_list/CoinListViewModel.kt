package com.yatish.cryptocurrency.ui.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatish.common.util.Resource
import com.yatish.domain.model.Coin
import com.yatish.domain.usecase.GetCoinsUseCase
import com.yatish.cryptocurrency.mapper.CoinErrorMapper
import com.yatish.cryptocurrency.ui.CoinState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinListUseCase: GetCoinsUseCase,
    private val coinErrorMapper: CoinErrorMapper,
) : ViewModel() {

    private val _state = mutableStateOf<CoinState>(CoinState.Loading(true))
    val state: State<CoinState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch {
            coinListUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CoinState.Success(result.data)
                    }
                    is Resource.Error -> {
                        updateErrorResult(result)
                    }
                    is Resource.Loading -> {
                        _state.value = CoinState.Loading(true)
                    }
                }

            }
        }
    }

    private fun updateErrorResult(result: Resource.Error<List<Coin>>) {
        _state.value = CoinState.Error(coinErrorMapper.mapToDomainLayer(result.errorEntity))
    }
}