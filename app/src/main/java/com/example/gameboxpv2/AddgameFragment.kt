package com.example.gameboxpv2.ui.games

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameboxpv2.databinding.FragmentAddgameBinding

class AddgameFragment : Fragment() {

    private var _binding: FragmentAddgameBinding? = null
    private val binding get() = _binding!!

    // ✅ Compartido con ErrorFragment
    private val vm: GamesViewModel by activityViewModels()

    private lateinit var adapter: RawgSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddgameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RawgSearchAdapter(onAddClick = { game ->
            vm.addToMyGames(game)  // 👈 guarda en “Mis Juegos”
            Toast.makeText(requireContext(), "Añadido: ${game.name}", Toast.LENGTH_SHORT).show()
        })

        binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResults.adapter = adapter

        vm.games.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        vm.error.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val q = s?.toString()?.trim().orEmpty()
                if (q.length >= 3) vm.searchGames(q) else adapter.submitList(emptyList())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
