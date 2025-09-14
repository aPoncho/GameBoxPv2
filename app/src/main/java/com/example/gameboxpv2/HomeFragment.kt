package com.example.gameboxpv2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager // Importante
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameTextView = view.findViewById<TextView>(R.id.textView_username)

        val username = arguments?.getString("USERNAME_KEY")
        Log.d("DATA_TRACE", "HomeFragment recibió: $username")

        if (!username.isNullOrEmpty()) {
            usernameTextView.text = username
        } else {
            usernameTextView.text = "Invitado"
        }

        // --- INICIO: CÓDIGO NUEVO PARA RECYCLERVIEW ---

        // 1. Prepara tu lista de datos de ejemplo
        val popularGames = listOf(
            Game("Mundo Gaturro", R.drawable.juego7), // Reemplaza con tus nombres de imagen
            Game("CyberPunk 2077", R.drawable.juego1),
            Game("Elden Ring", R.drawable.juego2)
        )

        // Puedes crear otra lista para el otro RecyclerView
        val newReleaseGames = listOf(
            Game("PEAK", R.drawable.juego3),
            Game("Clair Obscure: Expedition 33", R.drawable.juego4),
            Game("Resident Evil: Requiem", R.drawable.juego5)
        )

        // 2. Busca el RecyclerView en tu layout
        val popularRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_popular)
        val newReleaseRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_nuevos_lanzamientos)

        // 3. Configura el RecyclerView de Populares
        popularRecyclerView.layoutManager = GridLayoutManager(context, 3) // 3 columnas
        popularRecyclerView.adapter = GameAdapter(popularGames)

        // 4. Configura el RecyclerView de Nuevos Lanzamientos
        newReleaseRecyclerView.layoutManager = GridLayoutManager(context, 3) // 3 columnas
        newReleaseRecyclerView.adapter = GameAdapter(newReleaseGames)


    }
}