package com.example.gameboxpv2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val PLATFORMS = arrayOf(
        "PC",
        "Playstation 5",
        "Playstation 4",
        "Xbox Series X",
        "Nintendo Switch"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(Companion.ARG_PARAM1)
            param2 = it.getString(Companion.ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editProfileClickable = view.findViewById<RelativeLayout>(R.id.setting_edit_profile)

        editProfileClickable?.setOnClickListener {
            showPlatformSelectionDialog()
        }
    }

    private fun showPlatformSelectionDialog() {
        // Usamos la referencia directa a Companion
        val prefs = requireActivity().getSharedPreferences(
            Companion.PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val currentPlatform = prefs.getString(Companion.KEY_PLATFORM, PLATFORMS[0])
        var checkedItem = PLATFORMS.indexOf(currentPlatform)
        if (checkedItem == -1) checkedItem = 0

        AlertDialog.Builder(requireContext())
            .setTitle("Seleccionar Plataforma Principal")

            .setSingleChoiceItems(PLATFORMS, checkedItem) { dialog, which ->
                checkedItem = which
            }

            .setPositiveButton("Guardar") { dialog, _ ->
                val selectedPlatform = PLATFORMS[checkedItem]

                prefs.edit()
                    .putString(Companion.KEY_PLATFORM, selectedPlatform)
                    .apply()

                Toast.makeText(context, "Plataforma actualizada a: $selectedPlatform", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        const val PREFS_NAME = "gamebox_settings"
        const val KEY_PLATFORM = "preferred_platform"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}