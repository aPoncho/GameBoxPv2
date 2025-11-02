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

    private val _games = MutableLiveData<List<RawgGameDto>>(emptyList())
    val games: LiveData<List<RawgGameDto>> = _games

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadExampleGames() {
        viewModelScope.launch {
            try {
                val resp = RawgClient.api.getGames(
                    apiKey = BuildConfig.RAWG_API_KEY,
                    pageSize = 10
                )
                _games.value = resp.results
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error al cargar juegos"
            }
        }
    }
}
