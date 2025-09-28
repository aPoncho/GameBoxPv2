package com.example.gameboxpv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Encuentra el NavController desde el NavHostFragment en tu layout.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 2. Encuentra tu BottomNavigationView.
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 3. Conecta la barra de navegación con el NavController.
        // Esta línea se encarga de manejar los clics y cambiar de fragmento automáticamente.
        bottomNavigationView.setupWithNavController(navController)
    }
}