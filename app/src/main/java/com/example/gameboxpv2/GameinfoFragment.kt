package com.example.gameboxpv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class GameinfoFragment : Fragment(R.layout.fragment_gameinfo) {

    // Recibe los argumentos (el objeto Game) de forma segura.
    private val args: GameinfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Obtiene el juego que fue enviado.
        val selectedGame = args.selectedGame

        // 2. Busca las vistas usando los IDs CORRECTOS de tu XML.
        val gameBannerView = view.findViewById<ImageView>(R.id.game_banner_placeholder) // Nueva variable para el banner
        val gameImageView = view.findViewById<ImageView>(R.id.game_poster_placeholder)
        val gameTitleView = view.findViewById<TextView>(R.id.game_title)
        val gameDescriptionView = view.findViewById<TextView>(R.id.game_synopsis)

        // 3. Asigna la información del juego a las vistas.
        gameBannerView.setImageResource(selectedGame.bannerImage) // Usa la nueva propiedad para el banner
        gameImageView.setImageResource(selectedGame.coverImage)
        gameTitleView.text = selectedGame.title
        gameDescriptionView.text = selectedGame.description
    }
}