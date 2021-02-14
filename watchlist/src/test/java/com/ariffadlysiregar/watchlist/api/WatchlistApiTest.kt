package com.ariffadlysiregar.watchlist.api

import com.ariffadlysiregar.watchlist.utils.TestUtilities
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

class WatchlistApiTest {
    private val mockWebServer = MockWebServer()
    private lateinit var api: WatchlistApi

    @Before
    fun setup() {
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WatchlistApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get watchlist data - should return watchlist data`() = runBlocking {
        val limit = 50
        val currency = "IDR"

        val mockBody = TestUtilities.getResponseBodyFromJsonFile(
            filename = "http_response_watchlist_200.json"
        )

        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(mockBody)


        mockWebServer.enqueue(mockResponse)

        val response = api.getWatchlistAsync(limit, currency).await()

        val request = mockWebServer.takeRequest()

        val data = response.body()!!.data

        assert(response.isSuccessful)
        assert(request.method == "GET")
        assert(request.path == "/data/top/totaltoptiervolfull?limit=50&tsym=IDR")
        assert(data[0].coinInfo?.name == "BTC")
    }
}