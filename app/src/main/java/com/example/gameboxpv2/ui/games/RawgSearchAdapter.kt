package com.example.gameboxpv2.ui.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gameboxpv2.R
import com.example.gameboxpv2.data.remote.rawg.RawgGameDto
import com.example.gameboxpv2.databinding.ItemRawgGameBinding

class RawgSearchAdapter(
    private val onAddClick: (RawgGameDto) -> Unit
) : ListAdapter<RawgGameDto, RawgSearchAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<RawgGameDto>() {
        override fun areItemsTheSame(a: RawgGameDto, b: RawgGameDto) = a.id == b.id
        override fun areContentsTheSame(a: RawgGameDto, b: RawgGameDto) = a == b
    }

    inner class VH(private val binding: ItemRawgGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: RawgGameDto) = with(binding) {
            txtTitle.text = game.name
            txtSub.text = game.released ?: "Sin fecha"

            imgCover.load(game.backgroundImage ?: R.drawable.ic_videogame) {
                crossfade(true)
                placeholder(R.drawable.ic_videogame)
                error(R.drawable.ic_videogame)
            }

            // Evita doble click rápido
            btnAdd.setOnClickListener {
                btnAdd.isEnabled = false
                onAddClick(game)
                btnAdd.postDelayed({ btnAdd.isEnabled = true }, 500)
            }

            // (Opcional) click en toda la fila:
            // root.setOnClickListener { onAddClick(game) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        val binding = ItemRawgGameBinding.inflate(inf, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
