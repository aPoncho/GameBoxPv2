package com.example.gameboxpv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PopularFragment : Fragment(R.layout.fragment_popular) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Prepara tus datos de ejemplo para los juegos populares.
        val popularGames = listOf(
            Game("CyberPunk 2077", "Cyberpunk 2077 es un RPG de aventura y acción de mundo abierto ambientado en el futuro sombrío de Night City, una peligrosa megalópolis obsesionada con el poder, el glamur y las incesantes modificaciones corporales.", R.drawable.juego1, R.drawable.top1),
            Game("Elden Ring", "EL NUEVO JUEGO DE ROL Y ACCIÓN DE AMBIENTACIÓN FANTÁSTICA. Álzate, Sinluz, y que la gracia te guíe para abrazar el poder del Círculo de Elden y encumbrarte como señor del Círculo en las Tierras Intermedias.", R.drawable.juego2, R.drawable.top2),
            Game("PEAK", "PEAK es un juego cooperativo de escalada en el que un solo error puede ser fatal. Ya sea en solitario o en grupo, la única esperanza de salir de la misteriosa isla es subiendo hasta la cima de la montaña del centro. ¿Tenéis lo que hace falta para subir al PICO?", R.drawable.juego3, R.drawable.top3),
            Game("Clair Obscure", "Guía a la expedición 33 en su viaje para destruir a la Peintresse para que no pinte la muerte. Explora un mundo inspirado por la Francia de la Belle Époque y combate enemigos únicos en este juego de rol por turnos con mecánicas en tiempo real.", R.drawable.juego4, R.drawable.top4),
            Game("Resident Evil Requiem", "Réquiem para los muertos. Pesadilla para los vivos.", R.drawable.juego5, R.drawable.top5),
            Game("Left 4 Dead 2", "Ambientado en el apocalipsis zombi, Left 4 Dead 2 (L4D2) es la esperadísima secuela del galardonado Left 4 Dead, el juego cooperativo número 1 de 2008. Este FPS cooperativo de acción y terror os llevará a ti y a tus amigos por las ciudades, pantanos y cementerios.", R.drawable.juego6, R.drawable.top7),
            Game("Mundo Gaturro", "Mundo Gaturro es un videojuego multijugador masivo en línea (MMO), inspirado en el popular personaje de cómic Gaturro. Los jugadores controlaban avatares de gaturros (gatos antropomórficos) para interactuar con otros usuarios en un mundo virtual, disfrazar a sus personajes, jugar minijuegos, decorar casas y socializar en un entorno seguro con moderadores. ", R.drawable.juego7, R.drawable.top6),
            Game("Garfield Kart", "Garfield, el famoso gato amante de la lasaña, ha regresado para enfrentarse a Jon, Odie y compañía en un juego de carreras donde todo está permitido.", R.drawable.juego8, R.drawable.top8),
            Game("Rematch", "Rematch es un juego de fútbol multijugador online. Controla un jugador de tu equipo y compite en veloces partidos 5 contra 5 desde una perspectiva inmersiva en tercera persona. Forma equipo con tus amigos y disfrutad de la acción.", R.drawable.juego12, R.drawable.top12),
            Game("APEWAR", "Apewar es un juego en línea de acción táctica y disparos en primera y tercera persona (FPS/TPS) de guerra entre monos, inspirado en la vida real. Forma tu equipo de monos y lucha contra tus enemigos. Si juegas de manera coordinada, podrás divertirte y ganar con una buena estrategia. ¡Los monos son más fuertes juntos!", R.drawable.juego11, R.drawable.top11),
            Game("Portal 2", "¡La «Iniciativa de Prueba Perpetua» se ha ampliado, permitiéndote ahora diseñar puzles cooperativos para ti y tus amigos!", R.drawable.juego10, R.drawable.top10),
            Game("Uma Musume", "Umamusume: Pretty Derby está lista para correr. Recluta a las entrenadoras y al personal destacado mientras te adentras en esta simulación deportiva inmersiva, donde deberás entrenar a tus corredoras a través de un sistema de entrenamiento profundo y detallado.\n" +
                    "Disfruta de una experiencia visual impresionante gracias a sus gráficos 3D de primer nivel, y vive la emoción de las competencias mientras llevas a tu equipo a la victoria.", R.drawable.juego9, R.drawable.top)
        )

        // 2. Busca el RecyclerView en el layout.
        val popularRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_popular)

        // 3. Configura el RecyclerView con el adaptador y el listener de clic.
        popularRecyclerView.layoutManager = GridLayoutManager(context, 3)
        popularRecyclerView.adapter = GameAdapter(popularGames) { selectedGame ->
            // Al hacer clic, navega y envía el objeto del juego seleccionado.
            val action = PopularFragmentDirections.actionPopularFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }
    }
}