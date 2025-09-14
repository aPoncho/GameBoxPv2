package com.example.gameboxpv2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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


        if (savedInstanceState == null) {
            val username = intent.getStringExtra("USERNAME")
            Log.d("DATA_TRACE", "MainActivity recibió: $username")

            val homeFragment = HomeFragment()

            val bundle = Bundle()
            bundle.putString("USERNAME_KEY", username)

            homeFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .add(R.id.home_fragment_container, homeFragment)
                .commit()
        }
    }
}