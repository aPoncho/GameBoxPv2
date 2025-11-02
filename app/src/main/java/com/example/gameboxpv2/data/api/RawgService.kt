package com.example.gameboxpv2.data.api

import retrofit2.http.GET
import retrofit2.http.Query

// ----- DTOs mínimos -----

data class RawgPlatform(
    val id: Int,
    val name: String
)

data class RawgPlatformsResponse(
    val results: List<RawgPlatform>
)

data class RawgGame(
    val id: Long,
    val name: String,
    val released: String?,
    val rating: Double?,
    val background_image: String?
)

data class RawgGamesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<RawgGame>
)

// ----- Service -----
interface RawgService {

    // GET https://api.rawg.io/api/platforms?key=...
    @GET("platforms")
    suspend fun getPlatforms(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 40
    ): RawgPlatformsResponse

    // GET https://api.rawg.io/api/games?key=...&dates=...&platforms=...
    @GET("games")
    suspend fun getGames(
        @Query("dates") dates: String? = null,          // "2019-09-01,2019-09-30"
        @Query("platforms") platforms: String? = null,  // "18,1,7"
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 40,
        @Query("ordering") ordering: String? = "-rating",
        @Query("search") search: String? = null
    ): RawgGamesResponse
}
