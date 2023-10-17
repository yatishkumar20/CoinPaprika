package com.yatish.cryptocurrency.ui.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatish.common.util.Resource
import com.yatish.domain.model.CoinDetail
import com.yatish.domain.usecase.GetCoinDetailsUseCase
import com.yatish.cryptocurrency.mapper.CoinErrorMapper
import com.yatish.cryptocurrency.ui.CoinState
import com.yatish.cryptocurrency.util.Constants.PARAM_COIN_ID
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

    private val _state = mutableStateOf<CoinState<CoinDetail>>(CoinState.Loading(true))
    val state: State<CoinState<CoinDetail>> = _state

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
                        _state.value = CoinState.Success(result.data)
                    }
                    is Resource.Error -> {
                        updateErrorResult(result)
                    }
                }

            }
        }
    }

    private fun updateErrorResult(result: Resource.Error<CoinDetail>) {
        _state.value = CoinState.Error(
            error = coinErrorMapper.mapToDomainLayer(result.errorEntity)
        )
    }
}