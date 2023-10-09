package com.yatish.cryptocurrency.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yatish.cryptocurrency.ui.coin_detail.CoinDetailScreen
import com.yatish.cryptocurrency.ui.coin_list.CoinListScreen
import com.yatish.cryptocurrency.util.Constants.PARAM_COIN_ID
import com.yatish.cryptocurrency.util.Constants.PARAM_COIN_NAME

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
        composable(route = Screen.CoinDetailsScreen.route + "/{${PARAM_COIN_ID}}" + "/{${PARAM_COIN_NAME}}") {
            CoinDetailScreen()
        }
    }
}