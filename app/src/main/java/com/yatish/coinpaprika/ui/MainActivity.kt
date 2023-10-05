package com.yatish.coinpaprika.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.yatish.coinpaprika.ui.theme.CoinPaprikaTheme
import com.yatish.coinpaprika.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinPaprikaTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                var actionBarTitle by remember {
                    mutableStateOf(context.getString(R.string.app_name))
                }
                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { backStackEntry ->
                        actionBarTitle =
                            if (backStackEntry.destination.route == Screen.CoinDetailsScreen.route + "/{coinId}") {
                                ""
                            } else {
                                context.getString(R.string.app_name)
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
                                if (actionBarTitle.isBlank()) {
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
        }
    }
}