package com.yatish.cryptocurrency.ui.coin_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.yatish.feature.cryptocurrency.R
import com.yatish.cryptocurrency.ui.AppBar
import com.yatish.cryptocurrency.ui.coin_detail.component.CoinDetailsComponent
import com.yatish.cryptocurrency.util.Constants

@Composable
fun CoinDetailScreen(
    navController: NavController,
) {
    val title =
        navController.currentBackStackEntry?.arguments?.getString(Constants.PARAM_COIN_NAME)
            ?: LocalContext.current.getString(R.string.app_name)
    AppBar(
        title = title,
        true,
        content = { CoinDetailsComponent() }
    ) {
        navController.popBackStack()
    }
}