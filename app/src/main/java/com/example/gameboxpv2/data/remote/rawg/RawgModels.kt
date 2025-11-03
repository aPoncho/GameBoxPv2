package com.example.gameboxpv2.data.remote.rawg

import com.squareup.moshi.Json

data class RawgResponse(
    val count: Int?,
    val results: List<RawgGameDto>
)

data class RawgGameDto(
    val id: Int,
    val name: String,
    @Json(name = "released") val released: String?,
    @Json(name = "background_image") val backgroundImage: String?,
    val rating: Double?,
    val platforms: List<RawgPlatformHolder>?
)

data class RawgPlatformHolder(
    val platform: RawgPlatform?
)

data class RawgPlatform(
    val id: Int?,
    val name: String?
)
