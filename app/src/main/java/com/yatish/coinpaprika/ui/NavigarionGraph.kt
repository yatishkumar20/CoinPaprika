package com.yatish.coinpaprika.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yatish.coinpaprika.ui.coin_detail.CoinDetailScreen
import com.yatish.coinpaprika.ui.coin_list.CoinListScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CoinListScreen.route
    ) {
        composable(route = Screen.CoinListScreen.route) {
            CoinListScreen(navController = navController)
        }
        composable(route = Screen.CoinDetailsScreen.route + "/{coinId}") {
            CoinDetailScreen()
        }
    }
}