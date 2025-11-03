package com.example.gameboxpv2.data.remote.rawg

import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApi {

    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 10
    ): com.example.gameboxpv2.data.remote.rawg.RawgResponse
    // 👆 uso el nombre COMPLETO para que NO pueda importar el paquete malo
}
