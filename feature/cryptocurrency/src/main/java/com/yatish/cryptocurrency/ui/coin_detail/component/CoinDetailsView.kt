package com.yatish.cryptocurrency.ui.coin_detail.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import coil.compose.AsyncImage
import com.yatish.domain.model.CoinDetail
import com.yatish.cryptocurrency.common.TextView
import com.yatish.feature.cryptocurrency.R

@Composable
fun CoinDetailsView(
    coinDetails: CoinDetail
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextView(
                    text = "${coinDetails.rank}. ${coinDetails.name} (${coinDetails.symbol})",
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(20.dp))

                AsyncImage(
                    model = coinDetails.logo,
                    contentDescription = coinDetails.name,
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            TextView(
                text = coinDetails.description,
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextView(
                text = stringResource(id = R.string.tags),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(15.dp))
            FlowRow(
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                coinDetails.tags.forEach { tag ->
                    CoinTag(tag = tag)
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            TextView(
                text = stringResource(id = R.string.team_members),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
        items(coinDetails.team) { team ->
            TeamList(
                team = team,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Divider()
        }
    }
}