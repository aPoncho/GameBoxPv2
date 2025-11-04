package com.example.gameboxpv2.data.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude // Importante para IDs
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    @get:Exclude var id: String? = null, // ID del documento de Firestore, lo excluimos de la escritura
    val title: String = "",
    val platform: String = "",
    val status: String = "Jugando", // Puedes poner valores por defecto
    val rating: Float = 0.0f,
    val releaseYear: Int = 2024,
    val notes: String = "",
    val coverImageUrl: String = ""
) : Parcelable
