package com.yatish.cryptocurrency.ui.coin_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.yatish.cryptocurrency.ui.AppBar
import com.yatish.cryptocurrency.ui.coin_list.component.CoinListComponent
import com.yatish.feature.cryptocurrency.R

@Composable
fun CoinListScreen(
    navController: NavController,
) {
    val title = LocalContext.current.getString(R.string.app_name)
    AppBar(
        title = title,
        content = {
            CoinListComponent(
                navController = navController
            )
        }
    )
}