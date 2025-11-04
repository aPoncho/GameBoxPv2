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
import kotlinx.coroutines.launch // Importa launch
import androidx.lifecycle.viewModelScope // Importa viewModelScope

class GamesViewModel : ViewModel() {

    // Instancias de Firebase
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance() // 1. Obtenemos la instancia de Auth

    // Variable privada para guardar el ID del usuario actual
    private var currentUserId: String? = null // 2. Variable para almacenar el ID

    // LiveData para notificar el estado de la operación de guardado
    private val _saveStatus = MutableLiveData<Event<String>>()
    val saveStatus: LiveData<Event<String>> = _saveStatus

    // LiveData para los juegos del usuario
    private val _userGames = MutableLiveData<List<Game>>()
    val userGames: LiveData<List<Game>> = _userGames

    // LiveData para los resultados de la búsqueda de la API
    private val _searchResults = MutableLiveData<List<RawgGameDto>>()
    val searchResults: LiveData<List<RawgGameDto>> = _searchResults

    // LiveData para los errores de la API
    private val _apiError = MutableLiveData<Event<String>>()
    val apiError: LiveData<Event<String>> = _apiError

    // Instancia del cliente de la API
    private val api = RawgClient.api

    fun searchGames(query: String) {
        // viewModelScope lanza una corrutina que se cancela
        // automáticamente cuando el ViewModel se destruye.
        viewModelScope.launch {
            try {
                // Llama a la función 'suspend' del cliente de API
                val response = api.searchGames(BuildConfig.RAWG_API_KEY, query )
                // Actualiza el LiveData con los resultados
                _searchResults.value = response.results
            } catch (e: Exception) {
                // Si hay un error de red o de la API se notifica.
                _apiError.value = Event("Error de red: ${e.message}")
            }
        }
    }

    // --- LÓGICA DE AUTENTICACIÓN ---
    // El bloque init se ejecuta cuando el ViewModel se crea por primera vez.
    init {
        // Listener que se activa cada vez que el estado de auth cambie.
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // Si hay un usuario, guardamos su ID
                currentUserId = user.uid
                // Y cargamos sus juegos
                loadUserGames()
            } else {
                // Si no hay usuario (sesión cerrada), se limpia el ID y la lista de juegos
                currentUserId = null
                _userGames.value = emptyList()
            }
        }
        //Adjunta el Listener a la instancia de FirebaseAuth.
        auth.addAuthStateListener(authStateListener)
    }

    /**
     * Añade un juego a la biblioteca del usuario actual.
     */
    fun addToMyGames(game: RawgGameDto) {
        // en lugar de recibir el ID, se lee desde la variable interna.
        if (currentUserId == null) {
            _saveStatus.value = Event("Error: Debes iniciar sesión para guardar juegos.")
            return
        }

        val gameToSave = Game(
            title = game.name,
            coverImageUrl = game.backgroundImage ?: "",
            releaseYear = game.released?.substring(0, 4)?.toIntOrNull() ?: 0,
            rating = (game.rating ?: 0.0).toFloat(),
            platform = game.platforms?.joinToString(", ") { wrapper ->
                wrapper.platform?.name ?: "Plataforma desconocida"
            } ?: "No especificado",
            status = "Pendiente", // Valor por defecto
            notes = "" // Valor por defecto
        )

        //Usa el ID de usuario que ya esta guardado.
        db.collection("users").document(currentUserId!!)
            .collection("gameLibrary")
            .add(gameToSave)
            .addOnSuccessListener {
                _saveStatus.value = Event("Añadido: ${gameToSave.title}")
            }
            .addOnFailureListener { e ->
                _saveStatus.value = Event("Error al guardar: ${e.message}")
            }
    }

    // --- Carga los juegos del usuario
    fun loadUserGames() {
        if (currentUserId == null) {
            _userGames.value = emptyList() // No hay usuario, lista vacía
            return
        }

        db.collection("users").document(currentUserId!!)
            .collection("gameLibrary")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    // Manejar el error, por ahora simplemente limpiamos la lista
                    _userGames.value = emptyList()
                    return@addSnapshotListener
                }

                val gamesList = snapshots?.mapNotNull { it.toObject(Game::class.java) } ?: emptyList()
                _userGames.value = gamesList
            }
    }
}
