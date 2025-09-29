package com.example.gameboxpv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- 1. Prepara tus datos de ejemplo ---
        // (Asegúrate de tener estas imágenes en tu carpeta res/drawable)
        val popularGames = listOf(
            Game("CyberPunk 2077", "Cyberpunk 2077 es un RPG de aventura y acción de mundo abierto ambientado en el futuro sombrío de Night City, una peligrosa megalópolis obsesionada con el poder, el glamur y las incesantes modificaciones corporales.", R.drawable.juego1, R.drawable.top1),
            Game("Elden Ring", "EL NUEVO JUEGO DE ROL Y ACCIÓN DE AMBIENTACIÓN FANTÁSTICA. Álzate, Sinluz, y que la gracia te guíe para abrazar el poder del Círculo de Elden y encumbrarte como señor del Círculo en las Tierras Intermedias.", R.drawable.juego2, R.drawable.top2),
            Game("PEAK", "PEAK es un juego cooperativo de escalada en el que un solo error puede ser fatal. Ya sea en solitario o en grupo, la única esperanza de salir de la misteriosa isla es subiendo hasta la cima de la montaña del centro. ¿Tenéis lo que hace falta para subir al PICO?", R.drawable.juego3, R.drawable.top3)
        )


        val newReleaseGames = listOf(
            Game("Clair Obscure", "Guía a la expedición 33 en su viaje para destruir a la Peintresse para que no pinte la muerte. Explora un mundo inspirado por la Francia de la Belle Époque y combate enemigos únicos en este juego de rol por turnos con mecánicas en tiempo real.", R.drawable.juego4, R.drawable.top4),
            Game("Resident Evil", "Réquiem para los muertos. Pesadilla para los vivos.", R.drawable.juego5, R.drawable.top5),
            Game("Mundo Gaturro", "Mundo Gaturro es un videojuego multijugador masivo en línea (MMO), inspirado en el popular personaje de cómic Gaturro. Los jugadores controlaban avatares de gaturros (gatos antropomórficos) para interactuar con otros usuarios en un mundo virtual, disfrazar a sus personajes, jugar minijuegos, decorar casas y socializar en un entorno seguro con moderadores. ", R.drawable.juego7, R.drawable.top6)
        )

        // Busca los RecyclerViews en el layout
        val popularRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_popular)
        val newReleaseRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_nuevos_lanzamientos)

        // Configura el RecyclerView de Populares con su clic
        popularRecyclerView.layoutManager = GridLayoutManager(context, 3)
        popularRecyclerView.adapter = GameAdapter(popularGames) { selectedGame ->
            // Al hacer clic, navega y envía el objeto del juego seleccionado
            val action = HomeFragmentDirections.actionHomeFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }

        // Configura el RecyclerView de Nuevos Lanzamientos con su clic
        newReleaseRecyclerView.layoutManager = GridLayoutManager(context, 3)
        newReleaseRecyclerView.adapter = GameAdapter(newReleaseGames) { selectedGame ->
            // Hacemos lo mismo para la segunda lista
            val action = HomeFragmentDirections.actionHomeFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }
    }
}