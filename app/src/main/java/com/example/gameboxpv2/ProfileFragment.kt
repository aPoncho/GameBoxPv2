package com.example.gameboxpv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: ProfileSectionsPagerAdapter // Declara el adaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.tab_layout_profile)
        viewPager = view.findViewById(R.id.view_pager_profile)

        // Inicializa el adaptador PASANDO 'this' (ProfileFragment)
        // porque el ViewPager2 está DENTRO de este fragmento.
        pagerAdapter = ProfileSectionsPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // Vincula el TabLayout con el ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Aquí configuras el texto (o icono) de cada pestaña
            tab.text = when (position) {
                0 -> "Perfil"
                1 -> "Mis Juegos"
                2 -> "Estadisticas"
                else -> null
            }
        }.attach()
    }
    class ProfileSectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return 3 // Número de pestañas
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProfileInfoFragment() // Primer pestaña
                1 -> ErrorFragment() // TODO: agregar biblioteca
                2 -> StatisticsFragment() //TODO: agregar pestaña estadisticas
                else -> throw IllegalStateException("Posición de pestaña inválida: $position")
            }
        }
    }
}