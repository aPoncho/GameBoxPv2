package com.example.gameboxpv2.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gameboxpv2.R
// --- CORRECCIÓN 1: Importa tu modelo 'Game' de la base de datos ---
import com.example.gameboxpv2.data.model.Game

// --- CORRECCIÓN 2: El adapter ahora trabaja con objetos 'Game' ---
class MyGamesAdapter(
    private val onGameClicked: (Game) -> Unit = {}
) : RecyclerView.Adapter<MyGamesAdapter.VH>() {

    // --- CORRECCIÓN 3: La lista interna almacena objetos 'Game' ---
    private val items = mutableListOf<Game>()

    // --- CORRECCIÓN 4: La función submitList espera una lista de 'Game' ---
    fun submitList(newItems: List<Game>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // Considera usar DiffUtil para mejor rendimiento en el futuro
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val img: ImageView = view.findViewById(R.id.imageView_game_cover)
        private val title: TextView = view.findViewById(R.id.textView_game_title)

        // --- CORRECCIÓN 5: La función bind recibe un objeto 'Game' ---
        fun bind(g: Game) {
            // --- CORRECCIÓN 6: Usa las propiedades del objeto 'Game' ---
            title.text = g.title
            img.load(g.coverImageUrl) { crossfade(true) }
            itemView.setOnClickListener { onGameClicked(g) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game_grid, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

