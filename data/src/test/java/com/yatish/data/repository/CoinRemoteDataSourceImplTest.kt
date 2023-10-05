package com.yatish.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.yatish.data.error.ErrorHandlerImpl
import com.yatish.data.mapper.CoinDetailResponseMapper
import com.yatish.data.mapper.CoinListResponseMapper
import com.yatish.data.remote.CoinPaprikaApi
import com.yatish.domain.util.ErrorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@ExperimentalCoroutinesApi
internal class CoinRemoteDataSourceImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val server = MockWebServer()
    private val dispatcher = Dispatchers.IO
    private val listResponseMapper = CoinListResponseMapper()
    private val detailsResponseMapper = CoinDetailResponseMapper()
    private val errorHandler = ErrorHandlerImpl()
    private lateinit var service: CoinPaprikaApi

    @Before
    fun init() {
        server.start(8000)
        val BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient.Builder()
            .build()
        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(CoinPaprikaApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `GIVEN GetCoins WHEN called THEN should return list of coins`() = runTest {
        val jsonString = COIN_LIST
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val repository = CoinRemoteDataSourceImpl(
            dispatcher,
            service,
            listResponseMapper,
            detailsResponseMapper,
            errorHandler
        )
        val resp = repository.getCoins()
        Assert.assertEquals(resp.data!![0].name, "Bitcoin")
    }

    @Test
    fun `GIVEN GetCoins WHEN called THEN should return error`() = runTest {
        server.enqueue(MockResponse().setResponseCode(429))
        val repository = CoinRemoteDataSourceImpl(
            dispatcher,
            service,
            listResponseMapper,
            detailsResponseMapper,
            errorHandler
        )
        val resp = repository.getCoins()
        Assert.assertEquals(resp.errorEntity, ErrorEntity.TooManyRequests)
    }

    @Test
    fun `GIVEN GetCoins WHEN called THEN should return exception`() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val repository = CoinRemoteDataSourceImpl(
            dispatcher,
            service,
            listResponseMapper,
            detailsResponseMapper,
            errorHandler
        )
        val resp = repository.getCoins()
        Assert.assertEquals(resp.errorEntity, ErrorEntity.NetWork)
    }

    @Test
    fun `GIVEN GetCoinDetails WHEN called THEN should return coin details`() = runTest {
        val jsonString = COIN_DETAILS
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val repository = CoinRemoteDataSourceImpl(
            dispatcher,
            service,
            listResponseMapper,
            detailsResponseMapper,
            errorHandler
        )
        val resp = repository.getCoinDetails(BITCOIN_ID)
        Assert.assertEquals(resp.data?.name, BITCOIN)
    }

    @Test
    fun `GIVEN GetCoinDetails WHEN called THEN should return error`() = runTest {
        server.enqueue(MockResponse().setResponseCode(429))
        val repository = CoinRemoteDataSourceImpl(
            dispatcher,
            service,
            listResponseMapper,
            detailsResponseMapper,
            errorHandler
        )
        val resp = repository.getCoinDetails(BITCOIN_ID)
        Assert.assertEquals(resp.errorEntity, ErrorEntity.TooManyRequests)
    }

    companion object {
        private const val COIN_LIST = "[\n" +
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

        private const val COIN_DETAILS = "{\n" +
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

        private const val BITCOIN = "Bitcoin"
        private const val BITCOIN_ID = "btc-bitcoin"
    }
}