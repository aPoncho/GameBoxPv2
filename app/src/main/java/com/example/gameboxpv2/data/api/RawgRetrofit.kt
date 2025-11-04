package com.example.gameboxpv2.data.api

import com.example.gameboxpv2.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RawgRetrofit {

    private const val BASE_URL = "https://api.rawg.io/api/"

    // --- PASO 1: Define el interceptor de logs ---
    // Solo necesitamos uno. Imprimirá el cuerpo de la petición.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // --- PASO 2: Construye el cliente HTTP añadiendo los interceptores por separado ---
    private val client = OkHttpClient.Builder()
        // Interceptor 1: Añade la API Key a cada llamada
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val urlWithApiKey = originalRequest.url.newBuilder()
                .addQueryParameter("key", BuildConfig.RAWG_API_KEY)
                .build()
            val newRequest = originalRequest.newBuilder().url(urlWithApiKey).build()
            chain.proceed(newRequest)
        }
        // Interceptor 2: Añade el logger de red para poder depurar en Logcat
        .addInterceptor(loggingInterceptor)
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
