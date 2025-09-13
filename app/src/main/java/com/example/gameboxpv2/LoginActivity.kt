package com.example.gameboxpv2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fieldUser = findViewById<TextInputEditText>(R.id.tiUser)
        val fieldPassword = findViewById<TextInputEditText>(R.id.tiPassword)
        val buttonLogin = findViewById<Button>(R.id.btnLogin)

        buttonLogin.setOnClickListener{
            val user = fieldUser.text.toString().trim()
            val password = fieldPassword.text.toString().trim()

            if (user.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ambos campos son obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "User: $user PASS: $password", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java).apply {
                    putExtra("USERNAME", user)
                }
                startActivity(intent)
            }
        }
    }
}