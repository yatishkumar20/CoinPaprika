package com.yatish.cryptocurrency.ui.coin_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yatish.domain.model.Coin
import com.yatish.cryptocurrency.common.CircularProgressView
import com.yatish.cryptocurrency.common.TextView
import com.yatish.cryptocurrency.ui.CoinState
import com.yatish.cryptocurrency.ui.coin_list.component.CoinListView

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {

        when (state) {
            is CoinState.Success<*> -> {
                val dataList = state.data as List<*>?
                val coins = dataList?.let { item ->
                    item.map { it as Coin }
                } ?: emptyList()
                CoinListView(
                    coins = coins,
                    navController = navController
                )
            }
            is CoinState.Error -> {
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

            is CoinState.Loading -> {
                if (state.loading) {
                    CircularProgressView(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

}