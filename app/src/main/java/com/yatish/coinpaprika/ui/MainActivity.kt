package com.yatish.coinpaprika.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yatish.coinpaprika.ui.theme.CoinPaprikaTheme
import com.yatish.cryptocurrency.ui.CoinApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinPaprikaTheme {
                CoinApp()
            }
        }
    }
}
