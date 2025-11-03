package com.example.gameboxpv2.data.remote.rawg

import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApi {

    // lista “normal”
    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 10
    ): RawgResponse

    // 🔎 búsqueda
    @GET("games")
    suspend fun searchGames(
        @Query("key") apiKey: String,
        @Query("search") query: String,
        @Query("page_size") pageSize: Int = 10
    ): RawgResponse
}
