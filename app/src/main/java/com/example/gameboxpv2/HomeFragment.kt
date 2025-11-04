package com.example.gameboxpv2

import android.content.ContentResolver
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gameboxpv2.data.model.Game

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularGames = listOf(
            Game(
                title = "CyberPunk 2077",
                // ase usa la función para convertir el ID del drawable en una URL
                coverImageUrl = resourceToUri(R.drawable.juego1),
                platform = "PC, PS5, Xbox Series X/S",
                rating = 4.5f,
                releaseYear = 2020,
                status = "Jugado",
                // Usamos el texto que tenías como descripción para las notas
                notes = "Cyberpunk 2077 es un RPG de aventura y acción de mundo abierto ambientado en el futuro sombrío de Night City..."
            ),
            Game(
                title = "Elden Ring",
                coverImageUrl = resourceToUri(R.drawable.juego2),
                platform = "PC, PS5, Xbox Series X/S",
                rating = 4.9f,
                releaseYear = 2022,
                status = "Completado",
                notes = "EL NUEVO JUEGO DE ROL Y ACCIÓN DE AMBIENTACIÓN FANTÁSTICA. Álzate, Sinluz, y que la gracia te guíe..."
            ),
            Game(
                title = "PEAK",
                coverImageUrl = resourceToUri(R.drawable.juego3),
                platform = "PC",
                rating = 4.2f,
                releaseYear = 2024,
                status = "Jugando",
                notes = "PEAK es un juego cooperativo de escalada en el que un solo error puede ser fatal..."
            )
        )

        val newReleaseGames = listOf(
            Game(
                title = "Clair Obscure",
                coverImageUrl = resourceToUri(R.drawable.juego4),
                platform = "PC, PS5, Xbox Series X/S",
                rating = 0f, // Aún no ha salido
                releaseYear = 2025,
                status = "Pendiente",
                notes = "Guía a la expedición 33 en su viaje para destruir a la Peintresse para que no pinte la muerte..."
            ),
            Game(
                title = "Resident Evil",
                coverImageUrl = resourceToUri(R.drawable.juego5),
                platform = "Multiplataforma",
                rating = 4.8f,
                releaseYear = 1996, // El original
                status = "Clásico",
                notes = "Réquiem para los muertos. Pesadilla para los vivos."
            ),
            Game(
                title = "Mundo Gaturro",
                coverImageUrl = resourceToUri(R.drawable.juego7),
                platform = "Web",
                rating = 3.5f,
                releaseYear = 2010,
                status = "Archivado",
                notes = "Mundo Gaturro es un videojuego multijugador masivo en línea (MMO)..."
            )
        )

        val popularRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_popular)
        val newReleaseRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_nuevos_lanzamientos)

        popularRecyclerView.layoutManager = GridLayoutManager(context, 3)
        popularRecyclerView.adapter = GameAdapter(popularGames) { selectedGame ->
            // Al hacer clic, navega y envía el objeto del juego seleccionado
            val action = HomeFragmentDirections.actionHomeFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }

        newReleaseRecyclerView.layoutManager = GridLayoutManager(context, 3)
        newReleaseRecyclerView.adapter = GameAdapter(newReleaseGames) { selectedGame ->
            val action = HomeFragmentDirections.actionHomeFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }
    }
    // Función auxiliar para convertir un ID de recurso drawable en una URL de String
    // que Coil pueda entender.
    private fun resourceToUri(resourceId: Int): String {
        val context = requireContext()
        return "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/$resourceId"
    }
}