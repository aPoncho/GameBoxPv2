package com.example.gameboxpv2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gameboxpv2.ui.games.GamesViewModel
import com.example.gameboxpv2.ui.games.MyGamesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ErrorFragment : Fragment() {

    private val vm: GamesViewModel by activityViewModels()
    private lateinit var adapter: MyGamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_error, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fabAddGame = view.findViewById<FloatingActionButton>(R.id.fab_add_game)
        val rv = view.findViewById<RecyclerView>(R.id.rvMyGames)
        val emptyState = view.findViewById<View>(R.id.emptyState)

        adapter = MyGamesAdapter()
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        vm.userGames.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                emptyState.visibility = View.VISIBLE
                rv.visibility = View.GONE
            } else {
                emptyState.visibility = View.GONE
                rv.visibility = View.VISIBLE
                adapter.submitList(list)
            }
        }

        fabAddGame.setOnClickListener {
            findNavController().navigate(R.id.action_errorFragment_to_addGameFragment)
        }
    }
}
