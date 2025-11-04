package com.example.gameboxpv2.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels // <-- CORRECCIÓN 1: Usa 'activityViewModels'
import com.example.gameboxpv2.data.model.Game // <-- CORRECCIÓN 2: Importa tu modelo 'Game'
import com.example.gameboxpv2.databinding.FragmentGamesBinding

class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    // Usamos 'activityViewModels' para compartir la instancia del ViewModel con otros fragmentos
    private val vm: GamesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- CORRECCIÓN 3: El botón ahora actualiza la lista ---
        // La función `loadUserGames()` ya se llama automáticamente cuando el usuario
        // inicia sesión, pero podemos dejar este botón para una recarga manual si quieres.
        binding.btnLoadGames.setOnClickListener {
            vm.loadUserGames()
            Toast.makeText(requireContext(), "Actualizando lista...", Toast.LENGTH_SHORT).show()
        }

        // Este LiveData contiene la lista de juegos guardados en Firestore.
        vm.userGames.observe(viewLifecycleOwner) { list: List<Game> ->
            // Adaptamos el texto para que use las propiedades del objeto 'Game'
            binding.txtResult.text = list.joinToString("\n") { g ->
                "• ${g.title} (${g.releaseYear}) ⭐ ${g.rating}"
            }
        }


        vm.saveStatus.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                if (message.startsWith("Error")) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
