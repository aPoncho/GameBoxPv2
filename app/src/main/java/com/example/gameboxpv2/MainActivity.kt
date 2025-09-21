package com.example.gameboxpv2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.example.gameboxpv2.HomeFragment
import com.example.gameboxpv2.PopularFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        fun replaceFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        if (savedInstanceState == null) {
            val username = intent.getStringExtra("USERNAME") ?: "Usuario Invitado"
            Log.d("DATA_TRACE", "MainActivity recibió: $username")

            val homeFragment = HomeFragment()

            val bundle = Bundle()
            bundle.putString("USERNAME_KEY", username)

            homeFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit()
        }

        //cuando se toca un item del navigationbar
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_popular -> replaceFragment(PopularFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
                else -> false
            }
            true
        }
    }
}