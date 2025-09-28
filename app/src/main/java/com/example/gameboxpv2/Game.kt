package com.example.gameboxpv2

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val title: String,
    val description: String,
    @DrawableRes val coverImage: Int,
    @DrawableRes val bannerImage: Int // Nueva propiedad para la imagen del banner
) : Parcelable