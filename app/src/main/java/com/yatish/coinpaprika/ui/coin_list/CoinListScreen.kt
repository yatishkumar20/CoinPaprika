package com.yatish.coinpaprika.ui.coin_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yatish.coinpaprika.ui.Screen
import com.yatish.coinpaprika.ui.coin_list.component.CoinListItem
import com.yatish.coinpaprika.ui.coin_list.component.CoinListView
import com.yatish.coinpaprika.ui.common.CircularProgressView
import com.yatish.coinpaprika.ui.common.TextView
import com.yatish.domain.model.Coin

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {

        when (state) {
            is CoinListState.Success -> {
                CoinListView(
                    coins = state.data,
                    navController = navController
                )
            }
            is CoinListState.Error -> {
                if (state.error.isNotBlank()) {
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
            }

            is CoinListState.Loading -> {
                if (state.isLoading) {
                    CircularProgressView(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

}