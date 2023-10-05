package com.yatish.coinpaprika.ui.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatish.coinpaprika.mapper.CoinErrorMapper
import com.yatish.domain.util.Constants.PARAM_COIN_ID
import com.yatish.domain.util.Resource
import com.yatish.domain.model.CoinDetail
import com.yatish.domain.usecase.GetCoinDetailsUseCase
import com.yatish.domain.util.ErrorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val coinDetailsUseCase: GetCoinDetailsUseCase,
    private val coinErrorMapper: CoinErrorMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<CoinDetailState>(CoinDetailState.Loading(false))
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
                        _state.value = result.data?.let {
                            CoinDetailState.Success(
                                it
                            )
                        } ?: CoinDetailState.Error(coinErrorMapper.mapToDomainLayer(ErrorEntity.Unknown))
                    }
                    is Resource.Error -> {
                        updateErrorResult(result)
                    }
                    is Resource.Loading -> {
                        _state.value = CoinDetailState.Loading(
                             true
                        )
                    }
                }

            }
        }
    }

    private fun updateErrorResult(result: Resource.Error<CoinDetail>) {
        _state.value = CoinDetailState.Error(
            error = coinErrorMapper.mapToDomainLayer(result.errorEntity)
        )
    }
}