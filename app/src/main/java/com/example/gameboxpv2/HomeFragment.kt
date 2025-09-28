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
            Game("CyberPunk 2077", "Explora la vibrante y peligrosa Night City.", R.drawable.juego1),
            Game("Elden Ring", "Un vasto mundo de fantasía te espera, Sinluz.", R.drawable.juego2),
            Game("PEAK", "Descripción para el juego PEAK.", R.drawable.juego3)
        )

        val newReleaseGames = listOf(
            Game("Clair Obscure", "Descripción para Clair Obscure.", R.drawable.juego4),
            Game("Resident Evil", "Descripción para Resident Evil.", R.drawable.juego5),
            Game("Mundo Gaturro", "El clásico juego online que marcó una generación.", R.drawable.juego7)
        )

        // --- 2. Busca tus RecyclerViews en el layout ---
        val popularRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_popular)
        val newReleaseRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_nuevos_lanzamientos)

        // --- 3. Configura el RecyclerView de Populares con su clic ---
        popularRecyclerView.layoutManager = GridLayoutManager(context, 3)
        popularRecyclerView.adapter = GameAdapter(popularGames) { selectedGame ->
            // Al hacer clic, navega y envía el objeto del juego seleccionado
            val action = HomeFragmentDirections.actionHomeFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }

        // --- 4. Configura el RecyclerView de Nuevos Lanzamientos con su clic ---
        newReleaseRecyclerView.layoutManager = GridLayoutManager(context, 3)
        newReleaseRecyclerView.adapter = GameAdapter(newReleaseGames) { selectedGame ->
            // Hacemos lo mismo para la segunda lista
            val action = HomeFragmentDirections.actionHomeFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }
    }
}