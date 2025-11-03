package com.example.gameboxpv2.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gameboxpv2.R

data class Game(
    val title: String,
    val coverImageRes: Int // @DrawableRes
)

class GameAdapter(
    private val onGameClicked: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private val items = mutableListOf<Game>()

    fun submitList(newItems: List<Game>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coverImageView: ImageView = itemView.findViewById(R.id.imageView_game_cover)
        val titleTextView: TextView = itemView.findViewById(R.id.textView_game_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game_grid, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = items[position]
        holder.coverImageView.setImageResource(game.coverImageRes)
        holder.titleTextView.text = game.title
        holder.itemView.setOnClickListener { onGameClicked(game) }
    }
}
