package com.example.gameboxpv2

import android.content.ContentResolver
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gameboxpv2.data.model.Game

class PopularFragment : Fragment(R.layout.fragment_popular) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularGames = listOf(
            Game(
                title = "CyberPunk 2077",
                coverImageUrl = resourceToUri(R.drawable.juego1),
                platform = "PC, PS5",
                rating = 4.5f,
                releaseYear = 2020,
                status = "Jugado",
                notes = "Cyberpunk 2077 es un RPG de aventura y acción de mundo abierto..."
            ),
            Game(
                title = "Elden Ring",
                coverImageUrl = resourceToUri(R.drawable.juego2),
                platform = "Multiplataforma",
                rating = 4.9f,
                releaseYear = 2022,
                status = "Completado",
                notes = "EL NUEVO JUEGO DE ROL Y ACCIÓN DE AMBIENTACIÓN FANTÁSTICA..."
            ),
            Game(
                title = "PEAK",
                coverImageUrl = resourceToUri(R.drawable.juego3),
                platform = "PC",
                rating = 4.2f,
                releaseYear = 2024,
                status = "Jugando",
                notes = "PEAK es un juego cooperativo de escalada..."
            ),
            Game(
                title = "Clair Obscure",
                coverImageUrl = resourceToUri(R.drawable.juego4),
                platform = "PC, PS5, Xbox Series X/S",
                rating = 0f,
                releaseYear = 2025,
                status = "Pendiente",
                notes = "Guía a la expedición 33 en su viaje para destruir a la Peintresse..."
            ),
            Game(
                title = "Resident Evil Requiem",
                coverImageUrl = resourceToUri(R.drawable.juego5),
                platform = "Multiplataforma",
                rating = 4.8f,
                releaseYear = 2023, // Ejemplo
                status = "Clásico",
                notes = "Réquiem para los muertos. Pesadilla para los vivos."
            ),
            Game(
                title = "Left 4 Dead 2",
                coverImageUrl = resourceToUri(R.drawable.juego6),
                platform = "PC, Xbox 360",
                rating = 4.7f,
                releaseYear = 2009,
                status = "Clásico",
                notes = "Ambientado en el apocalipsis zombi, Left 4 Dead 2 (L4D2) es la esperadísima secuela..."
            ),
            Game(
                title = "Mundo Gaturro",
                coverImageUrl = resourceToUri(R.drawable.juego7),
                platform = "Web",
                rating = 3.5f,
                releaseYear = 2010,
                status = "Archivado",
                notes = "Mundo Gaturro es un videojuego multijugador masivo en línea (MMO)..."
            ),
            Game(
                title = "Garfield Kart",
                coverImageUrl = resourceToUri(R.drawable.juego8),
                platform = "PC, Móvil",
                rating = 4.0f,
                releaseYear = 2013,
                status = "Completado",
                notes = "Garfield, el famoso gato amante de la lasaña, ha regresado para enfrentarse a Jon, Odie y compañía..."
            ),
            Game(
                title = "Rematch",
                notes = "Rematch es un juego de fútbol multijugador online. Controla un jugador de tu equipo y compite en veloces partidos 5 contra 5 desde una perspectiva inmersiva en tercera persona. Forma equipo con tus amigos y disfrutad de la acción.",
                coverImageUrl = resourceToUri(R.drawable.juego12),
                platform = "PC, PS5, XBOX",
                rating = 4.5f,
                releaseYear = 2025,
                status = "Pendiente"
            ),
            Game(
                title = "APEWAR",
                coverImageUrl = resourceToUri(R.drawable.juego11),
                platform = "PC, Consolas",
                rating = 4.3f,
                releaseYear = 2024,
                status = "En beta",
                notes = "Apewar es un juego en línea de acción táctica y disparos en primera y tercera persona (FPS/TPS) de guerra entre monos, inspirado en la vida real. Forma tu equipo de monos y lucha contra tus enemigos. Si juegas de manera coordinada, podrás divertirte y ganar con una buena estrategia. ¡Los monos son más fuertes juntos!"
            ),
            Game(
                title = "Portal 2",
                coverImageUrl = resourceToUri(R.drawable.juego10),
                platform = "PC, PS3, Xbox 360",
                rating = 4.9f,
                releaseYear = 2011,
                status = "Clásico",
                notes = "¡La «Iniciativa de Prueba Perpetua» se ha ampliado, permitiéndote ahora diseñar puzles cooperativos para ti y tus amigos!"
            ),
            Game(
                title = "Uma Musume",
                coverImageUrl = resourceToUri(R.drawable.juego9),
                platform = "Móvil",
                rating = 4.6f,
                releaseYear = 2021,
                status = "Jugando",
                notes = "Umamusume: Pretty Derby está lista para correr. Recluta a las entrenadoras y al personal destacado mientras te adentras en esta simulación deportiva inmersiva, donde deberás entrenar a tus corredoras a través de un sistema de entrenamiento profundo y detallado.\n" +
                        "Disfruta de una experiencia visual impresionante gracias a sus gráficos 3D de primer nivel, y vive la emoción de las competencias mientras llevas a tu equipo a la victoria."

            )
        )

        val popularRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_popular)

        popularRecyclerView.layoutManager = GridLayoutManager(context, 3)
        popularRecyclerView.adapter = GameAdapter(popularGames) { selectedGame ->
            val action = PopularFragmentDirections.actionPopularFragmentToGameinfoFragment(selectedGame)
            findNavController().navigate(action)
        }
    }
    //Funcion para que coil reconozca las imagenes
    private fun resourceToUri(resourceId: Int): String {
        // Es importante que `context` no sea nulo, lo cual es seguro dentro de onViewCreated
        return "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${requireContext().packageName}/$resourceId"
    }
}