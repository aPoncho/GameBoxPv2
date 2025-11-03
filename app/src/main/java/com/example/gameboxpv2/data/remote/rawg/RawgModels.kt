package com.example.gameboxpv2.data.remote.rawg

data class RawgResponse(
    val results: List<RawgGameDto> = emptyList()
)

data class RawgGameDto(
    val id: Int,
    val name: String,
    val released: String?,
    val rating: Double?
)
