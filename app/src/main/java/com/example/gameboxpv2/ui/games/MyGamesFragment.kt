package com.example.gameboxpv2.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gameboxpv2.R
import com.example.gameboxpv2.data.remote.rawg.RawgGameDto

class MyGamesAdapter(
    private val onGameClicked: (RawgGameDto) -> Unit = {}
) : RecyclerView.Adapter<MyGamesAdapter.VH>() {

    private val items = mutableListOf<RawgGameDto>()

    fun submitList(newItems: List<RawgGameDto>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val img: ImageView = view.findViewById(R.id.imageView_game_cover)
        private val title: TextView = view.findViewById(R.id.textView_game_title)

        fun bind(g: RawgGameDto) {
            title.text = g.name
            img.load(g.backgroundImage) { crossfade(true) }
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
