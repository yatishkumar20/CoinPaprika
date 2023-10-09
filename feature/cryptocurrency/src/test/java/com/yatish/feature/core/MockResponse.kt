package com.yatish.feature.core

import com.google.gson.Gson
import com.yatish.domain.model.Coin
import com.yatish.domain.model.CoinDetail

object MockResponse {
    fun getMockCoins(): List<Coin> {
        val jsonString =
                    "{\n" +
                    "\"id\": \"btc-bitcoin\",\n" +
                    "\"name\": \"Bitcoin\",\n" +
                    "\"symbol\": \"BTC\",\n" +
                    "\"rank\": 1,\n" +
                    "\"is_new\": false,\n" +
                    "\"is_active\": true,\n" +
                    "\"type\": \"coin\"\n" +
                    "}\n"
        val coin = Gson().fromJson(jsonString, Coin::class.java)
        return listOf(coin)
    }

    fun getMockCoinDetails(): CoinDetail {
        val jsonString =
            "{\n" +
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
                    "  \"tags\": [\"Blockchain Service\"\n" +
                    "  ],\n" +
                    "  \"team\": [\n" +
                    "    {\n" +
                    "      \"id\": \"vitalik-buterin\",\n" +
                    "      \"name\": \"Vitalik Buterin\",\n" +
                    "      \"position\": \"Author\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"description\": \"Bitcoin is a cryptocurrency.\",\n" +
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
                    "      \"http://blockchain.com/explorer\"\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"links_extended\": [\n" +
                    "    {\n" +
                    "      \"url\": \"http://blockchain.com/explorer\",\n" +
                    "      \"type\": \"explorer\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"whitepaper\": {\n" +
                    "    \"link\": \"https://static.coinpaprika.com/storage/cdn/whitepapers/215.pdf\"\n" +
                    "  },\n" +
                    "  \"first_data_at\": \"2018-10-03T11:48:19Z\",\n" +
                    "  \"last_data_at\": \"2019-05-03T11:00:00\"\n" +
                    "}"
        return Gson().fromJson(jsonString, CoinDetail::class.java)
    }
}
