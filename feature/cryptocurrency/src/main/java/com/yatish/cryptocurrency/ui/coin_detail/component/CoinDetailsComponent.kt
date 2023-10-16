package com.yatish.cryptocurrency.ui.coin_detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yatish.cryptocurrency.common.CircularProgressView
import com.yatish.cryptocurrency.common.TextView
import com.yatish.cryptocurrency.ui.CoinState
import com.yatish.cryptocurrency.ui.coin_detail.CoinDetailsViewModel
import com.yatish.domain.model.CoinDetail
import com.yatish.feature.cryptocurrency.R

@Composable
fun CoinDetailsComponent(
    viewModel: CoinDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {

        when (state) {
            is CoinState.Success<*> -> {
                val coinDetail = state.data as CoinDetail?
                coinDetail?.let {
                    CoinDetailsView(it)
                } ?: TextView(
                    text = stringResource(id = R.string.not_found),
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }

            is CoinState.Error -> {
                TextView(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }

            is CoinState.Loading -> {
                if (state.loading) {
                    CircularProgressView(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}