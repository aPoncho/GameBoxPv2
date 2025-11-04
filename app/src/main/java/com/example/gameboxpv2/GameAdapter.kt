package com.example.gameboxpv2
// En tu archivo GameAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gameboxpv2.data.model.Game
import coil.load // Importante importar la función de Coil

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
        holder.coverImageView.load(game.coverImageUrl) {
            crossfade(true) // Animación suave
            placeholder(R.drawable.ic_placeholder) // Imagen a mostrar mientras carga
            error(R.drawable.ic_error_placeholder) // Imagen si falla la carga
        }
        holder.titleTextView.text = game.title


        holder.itemView.setOnClickListener {
            onGameClicked(game)
        }
    }
}