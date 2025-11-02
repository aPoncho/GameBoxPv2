package com.example.gameboxpv2.data.api

import com.example.gameboxpv2.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RawgRetrofit {

    private const val BASE_URL = "https://api.rawg.io/api/"

    // agrega ?key=TU_KEY a todas las requests
    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .addQueryParameter("key", BuildConfig.RAWG_API_KEY)
            .build()
        val newReq = original.newBuilder().url(newUrl).build()
        chain.proceed(newReq)
    }

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(logger)
        .build()

    val api: RawgService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RawgService::class.java)
    }
}
