package com.yatish.coinpaprika.ui.coin_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.yatish.coinpaprika.ui.coin_detail.component.CoinTag
import com.yatish.coinpaprika.ui.coin_detail.component.TeamList
import com.yatish.coinpaprika.ui.common.CircularProgressView
import com.yatish.coinpaprika.ui.common.TextView
import com.yatish.coinpaprika.R
import com.yatish.coinpaprika.ui.coin_detail.component.CoinDetailsView
import com.yatish.domain.model.CoinDetail

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {

        when (state) {
            is CoinDetailState.Success -> {
                CoinDetailsView(state.coin)
            }

            is CoinDetailState.Error -> {
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

            is CoinDetailState.Loading -> {
                if (state.isLoading) {
                    CircularProgressView(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}