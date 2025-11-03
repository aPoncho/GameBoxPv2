package com.example.gameboxpv2.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameboxpv2.BuildConfig
import com.example.gameboxpv2.data.remote.rawg.RawgClient
import com.example.gameboxpv2.data.remote.rawg.RawgGameDto
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel() {

    // Resultados de RAWG (búsqueda o ejemplo)
    private val _games = MutableLiveData<List<RawgGameDto>>(emptyList())
    val games: LiveData<List<RawgGameDto>> = _games

    // Lista del usuario: “Mis Juegos”
    private val _myGames = MutableLiveData<List<RawgGameDto>>(emptyList())
    val myGames: LiveData<List<RawgGameDto>> = _myGames

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /** Cargar 10 juegos de ejemplo (botón “Cargar juegos RAWG”) */
    fun loadExampleGames() {
        viewModelScope.launch {
            try {
                val resp = RawgClient.api.getGames(
                    apiKey = BuildConfig.RAWG_API_KEY,
                    pageSize = 10
                )
                _games.value = resp.results
            } catch (e: Exception) {
                _error.value = e.message ?: "Error cargando juegos"
            }
        }
    }

    /** Buscar por nombre (lo usa AddgameFragment al tipear) */
    fun searchGames(query: String) {
        viewModelScope.launch {
            try {
                val resp = RawgClient.api.searchGames(
                    apiKey = BuildConfig.RAWG_API_KEY,
                    query = query,
                    pageSize = 10
                )
                _games.value = resp.results
            } catch (e: Exception) {
                _error.value = e.message ?: "Error buscando juegos"
            }
        }
    }

    /** Añadir un juego a “Mis Juegos” evitando duplicados por id */
    fun addToMyGames(game: RawgGameDto) {
        val current = _myGames.value?.toMutableList() ?: mutableListOf()
        if (current.none { it.id == game.id }) {
            current.add(0, game) // lo último añadido arriba
            _myGames.value = current
        }
    }
}
