package com.example.gameboxpv2.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameboxpv2.BuildConfig
import com.example.gameboxpv2.data.remote.rawg.RawgClient
import com.example.gameboxpv2.data.remote.rawg.RawgGameDto
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.gameboxpv2.data.model.Game // Modelo para Firestore
//import com.example.gameboxpv2.data.model.RawgGame //Modelo de la API

class GamesViewModel : ViewModel() {

    // Instancias de Firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Resultados de RAWG (búsqueda o ejemplo)
    private val _games = MutableLiveData<List<RawgGameDto>>(emptyList())
    val games: LiveData<List<RawgGameDto>> = _games

    // Lista del usuario: “Mis Juegos”
    private val _myGames = MutableLiveData<List<RawgGameDto>>(emptyList())
    val myGames: LiveData<List<RawgGameDto>> = _myGames

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // LiveData para el estado del guardado
    private val _saveStatus = MutableLiveData<Event<String>>()
    val saveStatus: LiveData<Event<String>> = _saveStatus

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
        val currentUser = auth.currentUser
        if (currentUser == null) {
            _saveStatus.value = Event("Error: Debes iniciar sesión para guardar juegos.")
            return
        }
        // Convierte el objeto de la API al modelo de Firestore 'Game'
        val gameToSave = Game(
            title = game.name,
            coverImageUrl = game.backgroundImage ?: "", //podria no existir una URL
            releaseYear = game.released?.substring(0, 4)?.toIntOrNull() ?: 0,
            rating = (game.rating ?: 0.0).toFloat(), //se pone el 0.0 como valor por defecto
            platform = game.platforms?.joinToString(", ") { wrapper ->
                // Si 'wrapper.platform' es nulo, o 'wrapper.platform.name' es nulo, usa "Plataforma desconocida"
                wrapper.platform?.name ?: "Plataforma desconocida"
            } ?: "No especificado"

            // TODO: añadir más campos (status, notes, etc.)
        )

        // Usa la colección 'users', el documento del usuario actual, y la subcolección 'gameLibrary'
        db.collection("users").document(currentUser.uid)
            .collection("gameLibrary")
            .add(gameToSave) // .add() crea un documento con un ID automático
            .addOnSuccessListener {
                // Éxito: Notifica al Fragment con un mensaje positivo
                _saveStatus.value = Event("Añadido: ${gameToSave.title}")
            }
            .addOnFailureListener { e ->
                // Error: Notifica al Fragment con el detalle del error
                _saveStatus.value = Event("Error al guardar: ${e.message}")
            }
    }
}
