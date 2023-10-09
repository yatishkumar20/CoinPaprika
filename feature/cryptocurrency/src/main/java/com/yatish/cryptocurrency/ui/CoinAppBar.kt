package com.yatish.cryptocurrency.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.yatish.feature.cryptocurrency.R
import com.yatish.cryptocurrency.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val appName = context.getString(R.string.app_name)
    var actionBarTitle by remember {
        mutableStateOf(appName)
    }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            actionBarTitle =
                if (backStackEntry.destination.route == Screen.CoinDetailsScreen.route + "/{${Constants.PARAM_COIN_ID}}"+ "/{${Constants.PARAM_COIN_NAME}}") {
                    backStackEntry.arguments?.getString(Constants.PARAM_COIN_NAME).toString()
                } else {
                    appName
                }
        }
    }

    Scaffold(topBar = {

        Column(modifier = Modifier.height(48.dp)) {
            CenterAlignedTopAppBar(
                title = {
                    Text(actionBarTitle, color = Color.White)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    MaterialTheme.colors.primaryVariant
                ),
                navigationIcon = {
                    if (actionBarTitle != appName) {
                        Image(
                            painter = painterResource(com.google.android.material.R.drawable.abc_ic_ab_back_material),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(20.dp, 20.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                    }
                }
            )
        }
    },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavigationGraph(navController = navController)
            }
        }
    )
}