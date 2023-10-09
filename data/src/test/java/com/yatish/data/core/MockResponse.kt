package com.yatish.data.core

import com.google.gson.Gson
import com.yatish.data.mapper.CoinDetailResponseMapper
import com.yatish.data.mapper.CoinListResponseMapper
import com.yatish.data.remote.dto.CoinDetailDto
import com.yatish.data.remote.dto.CoinDto
import com.yatish.domain.model.Coin
import com.yatish.domain.model.CoinDetail

object MockResponse {
    fun getMockCoinDto(): List<CoinDto> {
        return Gson().fromJson(coinList, Array<CoinDto>::class.java).asList()
    }

    fun getMockCoin(): List<Coin> {
        val mapper = CoinListResponseMapper()
        return Gson().fromJson(coinList, Array<CoinDto>::class.java).asList().map { mapper.mapToDomainLayer(it) }
    }

    fun getMockCoinDetailsDto(): CoinDetailDto {
        return Gson().fromJson(coinDetails, CoinDetailDto::class.java)
    }

    fun getMockCoinDetails(): CoinDetail {
        val mapper = CoinDetailResponseMapper()
        return with(Gson().fromJson(coinDetails, CoinDetailDto::class.java)) {
            mapper.mapToDomainLayer(this)
        }
    }

    private const val coinList: String = "[\n" +
            "  {\n" +
            "    \"id\": \"btc-bitcoin\",\n" +
            "    \"name\": \"Bitcoin\",\n" +
            "    \"symbol\": \"BTC\",\n" +
            "    \"rank\": 1,\n" +
            "    \"is_new\": false,\n" +
            "    \"is_active\": true,\n" +
            "    \"type\": \"coin\"\n" +
            "  }\n" +
            "]"

    private const val coinDetails: String = "{\n" +
            "  \"id\": \"btc-bitcoin\",\n" +
            "  \"name\": \"Bitcoin\",\n" +
            "  \"symbol\": \"BTC\",\n" +
            "  \"parent\": {\n" +
            "    \"id\": \"eth-ethereum\",\n" +
            "    \"name\": \"Ethereum\",\n" +
            "    \"symbol\": \"ETH\"\n" +
            "  },\n" +
            "  \"rank\": 1,\n" +
            "  \"is_new\": false,\n" +
            "  \"is_active\": true,\n" +
            "  \"type\": \"coin\",\n" +
            "  \"logo\": \"https://static.coinpaprika.com/coin/bnb-binance-coin/logo.png\",\n" +
            "  \"tags\": [\n" +
            "    {\n" +
            "      \"id\": \"blockchain-service\",\n" +
            "      \"name\": \"Blockchain Service\",\n" +
            "      \"coin_counter\": 160,\n" +
            "      \"ico_counter\": 80\n" +
            "    }\n" +
            "  ],\n" +
            "  \"team\": [\n" +
            "    {\n" +
            "      \"id\": \"vitalik-buterin\",\n" +
            "      \"name\": \"Vitalik Buterin\",\n" +
            "      \"position\": \"Author\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"description\": \"Bitcoin is a cryptocurrency and worldwide payment system. It is the first decentralized digital currency, as the system works without a central bank or single administrator.\",\n" +
            "  \"message\": \"string\",\n" +
            "  \"open_source\": true,\n" +
            "  \"hardware_wallet\": true,\n" +
            "  \"started_at\": \"2009-01-03T00:00:00Z\",\n" +
            "  \"development_status\": \"Working product\",\n" +
            "  \"proof_type\": \"Proof of work\",\n" +
            "  \"org_structure\": \"Decentralized\",\n" +
            "  \"hash_algorithm\": \"SHA256\",\n" +
            "  \"contract\": \"string\",\n" +
            "  \"platform\": \"string\",\n" +
            "  \"contracts\": [\n" +
            "    {\n" +
            "      \"contract\": \"string\",\n" +
            "      \"platform\": \"string\",\n" +
            "      \"type\": \"string\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"links\": {\n" +
            "    \"explorer\": [\n" +
            "      \"http://blockchain.com/explorer\",\n" +
            "      \"https://blockchair.com/bitcoin/blocks\",\n" +
            "      \"https://blockexplorer.com/\",\n" +
            "      \"https://live.blockcypher.com/btc/\"\n" +
            "    ],\n" +
            "    \"facebook\": [\n" +
            "      \"https://www.facebook.com/bitcoins/\"\n" +
            "    ],\n" +
            "    \"reddit\": [\n" +
            "      \"https://www.reddit.com/r/bitcoin\"\n" +
            "    ],\n" +
            "    \"source_code\": [\n" +
            "      \"https://github.com/bitcoin/bitcoin\"\n" +
            "    ],\n" +
            "    \"website\": [\n" +
            "      \"https://bitcoin.org/\"\n" +
            "    ],\n" +
            "    \"youtube\": [\n" +
            "      \"https://www.youtube.com/watch?v=Um63OQz3bjo\"\n" +
            "    ],\n" +
            "    \"medium\": null\n" +
            "  },\n" +
            "  \"links_extended\": [\n" +
            "    {\n" +
            "      \"url\": \"http://blockchain.com/explorer\",\n" +
            "      \"type\": \"explorer\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"url\": \"https://www.reddit.com/r/bitcoin\",\n" +
            "      \"type\": \"reddit\",\n" +
            "      \"stats\": {\n" +
            "        \"subscribers\": 1009135\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"url\": \"https://github.com/bitcoin/bitcoin\",\n" +
            "      \"type\": \"source_code\",\n" +
            "      \"stats\": {\n" +
            "        \"contributors\": 730,\n" +
            "        \"stars\": 36613\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"url\": \"https://bitcoin.org/\",\n" +
            "      \"type\": \"website\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"whitepaper\": {\n" +
            "    \"link\": \"https://static.coinpaprika.com/storage/cdn/whitepapers/215.pdf\",\n" +
            "    \"thumbnail\": \"https://static.coinpaprika.com/storage/cdn/whitepapers/217.jpg\"\n" +
            "  },\n" +
            "  \"first_data_at\": \"2018-10-03T11:48:19Z\",\n" +
            "  \"last_data_at\": \"2019-05-03T11:00:00\"\n" +
            "}"

    const val ERROR_429 = "{\n" +
            "\"error\": \"you have reached maximum request limit\"\n" +
            "}"
}
