package com.moneylion.expensetracker.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * API Util class to provide retrofit instance
 * It has example implementation of how an API call will look like
 * We will inject the retrofit instance to the API using Dependency Injection
 */
object APIUtil {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }
}