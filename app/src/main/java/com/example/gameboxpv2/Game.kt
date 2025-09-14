package com.example.gameboxpv2

// El @DrawableRes le dice a Android que este Int debe ser una referencia a una imagen.
import androidx.annotation.DrawableRes

data class Game(
    val title: String,
    @DrawableRes val coverImage: Int
)