package com.example.gameboxpv2.ui.games // o el paquete que elijas

/**
 * Usado como un wrapper para datos (eventos) expuestos via LiveData.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /**
     * Devuelve el contenido y evita que se use de nuevo.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
