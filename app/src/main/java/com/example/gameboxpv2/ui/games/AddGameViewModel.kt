package com.example.gameboxpv2.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameboxpv2.BuildConfig
import com.example.gameboxpv2.data.remote.rawg.RawgClient
import com.example.gameboxpv2.data.remote.rawg.RawgGameDto
import kotlinx.coroutines.launch

class AddGameViewModel : ViewModel() {

    private val _results = MutableLiveData<List<RawgGameDto>>(emptyList())
    val results: LiveData<List<RawgGameDto>> = _results

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun search(query: String) {
        if (query.isBlank()) {
            _results.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                val resp = RawgClient.api.searchGames(
                    query = query,
                    apiKey = BuildConfig.RAWG_API_KEY
                )
                _results.value = resp.results
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error buscando juegos"
            }
        }
    }
}