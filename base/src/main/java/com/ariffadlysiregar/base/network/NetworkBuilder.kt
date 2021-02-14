package com.ariffadlysiregar.base.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkBuilder {
    fun <T> create(
        baseUrl: String,
        apiType: Class<T>
    ) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(gmHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(apiType)

    private fun gmHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .retryOnConnectionFailure(true)


        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            chain.proceed(requestBuilder.build())
        }

        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Apikey ee7b0af3ad8cc13a6e90cb338882beaab62a46b35fd3355145d249c1b2f366c9")
            chain.proceed(requestBuilder.build())
        }

        return builder.build()
    }
}