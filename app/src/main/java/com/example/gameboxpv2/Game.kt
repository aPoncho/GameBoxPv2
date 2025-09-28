package com.example.gameboxpv2

// El @DrawableRes le dice a Android que este Int debe ser una referencia a una imagen.
// En tu archivo Game.kt

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize // <-- ¡Importante!

@Parcelize
data class Game(
    val title: String,
    val description: String,
    @DrawableRes val coverImage: Int
) : Parcelable