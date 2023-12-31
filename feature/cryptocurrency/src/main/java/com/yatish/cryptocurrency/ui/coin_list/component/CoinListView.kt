package com.yatish.cryptocurrency.ui.coin_list.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.yatish.domain.model.Coin
import com.yatish.cryptocurrency.ui.Screen


@Composable
fun CoinListView(
    coins: List<Coin>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(coins) { coin ->
            CoinListItem(
                coin = coin,
                onItemClick = {
                    navController.navigate(Screen.CoinDetailsScreen.route + "/${coin.id}" + "/${coin.name}")
                }
            )
        }
    }

}