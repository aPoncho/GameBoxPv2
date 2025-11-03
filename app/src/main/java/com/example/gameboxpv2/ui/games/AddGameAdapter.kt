// com/example/gameboxpv2/ui/games/AddGameAdapter.kt
package com.example.gameboxpv2.ui.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameboxpv2.data.remote.rawg.RawgGameDto
import com.example.gameboxpv2.databinding.ItemSearchGameBinding

class AddGameAdapter(
    private val onAddClick: (RawgGameDto) -> Unit
) : RecyclerView.Adapter<AddGameAdapter.VH>() {

    private val items = mutableListOf<RawgGameDto>()

    fun submit(list: List<RawgGameDto>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemSearchGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: RawgGameDto) {
            binding.txtName.text = game.name
            binding.txtPlatform.text = game.released ?: "Sin fecha"
            binding.btnAdd.setOnClickListener {
                onAddClick(game)
            }
            // si quieres, aquí puedes usar Glide/Picasso para game.background_image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        val binding = ItemSearchGameBinding.inflate(inf, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
