package com.yatish.coinpaprika.ui.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatish.coinpaprika.mapper.CoinErrorMapper
import com.yatish.domain.util.Resource
import com.yatish.domain.model.Coin
import com.yatish.domain.usecase.GetCoinsUseCase
import com.yatish.domain.util.ErrorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinListUseCase: GetCoinsUseCase,
    private val coinErrorMapper: CoinErrorMapper,
) : ViewModel() {

    private val _state = mutableStateOf<CoinListState>(CoinListState.Loading(false))
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch {
            coinListUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value =
                            result.data?.let { CoinListState.Success(it) } ?: CoinListState.Error(
                                coinErrorMapper.mapToDomainLayer(ErrorEntity.Unknown)
                            )
                    }
                    is Resource.Error -> {
                        updateErrorResult(result)
                    }
                    is Resource.Loading -> {
                        _state.value = CoinListState.Loading(true)
                    }
                }

            }
        }
    }

    private fun updateErrorResult(result: Resource.Error<List<Coin>>) {
        _state.value = CoinListState.Error(coinErrorMapper.mapToDomainLayer(result.errorEntity))
    }
}