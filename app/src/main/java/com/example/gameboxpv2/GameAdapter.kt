package com.example.gameboxpv2
// En tu archivo GameAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//                                      👇 CAMBIO 1: Añade el parámetro para el clic
class GameAdapter(
    private val gameList: List<Game>,
    private val onGameClicked: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coverImageView: ImageView = itemView.findViewById(R.id.imageView_game_cover)
        val titleTextView: TextView = itemView.findViewById(R.id.textView_game_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game_grid, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        holder.coverImageView.setImageResource(game.coverImage)
        holder.titleTextView.text = game.title

        // 👇 CAMBIO 2: Configura el listener para el clic
        // Esto hace que todo el item sea clicleable
        holder.itemView.setOnClickListener {
            // Cuando se hace clic, se ejecuta la función que nos pasaron
            onGameClicked(game)
        }
    }
}