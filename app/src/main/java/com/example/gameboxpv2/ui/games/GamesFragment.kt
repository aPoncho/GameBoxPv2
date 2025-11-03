package com.example.gameboxpv2.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gameboxpv2.databinding.FragmentGamesBinding

class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    private val vm: GamesViewModel by viewModels()  // 👈 este, no el otro

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLoadGames.setOnClickListener {
            vm.loadExampleGames()
        }

        vm.games.observe(viewLifecycleOwner) { list ->
            binding.txtResult.text = list.joinToString("\n") { g ->
                "• ${g.name} (${g.released ?: "N/D"}) ⭐ ${g.rating ?: 0.0}"
            }
        }

        vm.error.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg.take(200), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
