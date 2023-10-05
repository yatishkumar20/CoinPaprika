package com.yatish.coinpaprika.ui.coin_list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yatish.coinpaprika.ui.common.TextView
import com.yatish.domain.model.Coin
import com.yatish.coinpaprika.R

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(coin.id)
            }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextView(
            text = "${coin.rank}. ${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(20.dp))

        TextView(
            text = if (coin.isActive) stringResource(id = R.string.active) else stringResource(id = R.string.inactive),
            color = if (coin.isActive) Color.Green else Color.Red,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoinListItemPreview() {
    CoinListItem(
        coin = Coin(
            "1",
            isActive = true,
            symbol = "BTC",
            name = "Bitcoin",
            rank = 1
        ),
        onItemClick = { }
    )
}