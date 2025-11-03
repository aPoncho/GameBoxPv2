package com.example.gameboxpv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)   // usa tu activity_main con el FragmentContainerView

        // 1. Obtener el NavHost
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 2. Obtener el bottom nav
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 3. Conectarlo al navController
        bottomNavigationView.setupWithNavController(navController)

        // (opcional) evitar que al tocar el mismo item se “reinicie”
        bottomNavigationView.setOnItemReselectedListener {
            // no hacer nada
        }
    }
}
