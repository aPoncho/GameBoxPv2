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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class RegisterActivity : AppCompatActivity() {

    // 1. Declarar la instancia de FirebaseAuth.
    // 'lateinit' significa que la inicializaremos más tarde (en onCreate).
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // Declarar las vistas de la UI
    private lateinit var userEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Código para el padding y vistas
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Inicializar FirebaseAuth y firestore
        // getInstance() es la forma estándar de obtener el objeto de autenticación.
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // 3. Asociar las vistas con sus IDs del layout XML
        userEditText = findViewById(R.id.editText_username)
        emailEditText = findViewById(R.id.editText_email)
        passwordEditText = findViewById(R.id.editText_password)
        registerButton = findViewById(R.id.button_register)

        // 4. Configurar el listener para el botón de registro
        registerButton.setOnClickListener {
            // Llama a la función para registrar al usuario
            performRegistration()
        }
    }

    private fun performRegistration() {
        // 5. Obtener el texto de los campos y quitar espacios en blanco al inicio/final
        val username = userEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // 6. Validar que los campos no estén vacíos
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return // Detiene la función si hay un error
        }

        // 7. Llamar a Firebase para crear el usuario
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                // 8. Manejar el resultado de la tarea
                if (task.isSuccessful) {
                    // Si el registro en Authentication es exitoso, ahora guardamos los datos del usuario.
                    Toast.makeText(this, "Autenticación exitosa. Guardando datos...", Toast.LENGTH_SHORT).show()

                    // Obtenemos el ID único del usuario recién creado
                    val firebaseUser = auth.currentUser
                    val userId = firebaseUser?.uid

                    if (userId != null) {
                        // 2. Crear el documento en Firestore
                        saveUserInFirestore(userId, username, email)
                    } else {
                        Toast.makeText(this, "Error al obtener el ID del usuario.", Toast.LENGTH_LONG).show()
                    }

                } else {
                    // Si el registro falla, muestra un mensaje al usuario.
                    // 'task.exception?.message' puede dar detalles del error (ej: contraseña débil, email mal formateado, etc.)
                    Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
    // NUEVA FUNCIÓN para guardar los datos en Firestore
    private fun saveUserInFirestore(userId: String, username: String, email: String) {
        // Creamos un mapa (similar a un JSON) con los datos del usuario
        val userMap = hashMapOf(
            "username" to username,
            "email" to email
            // Aquí puedes añadir más campos iniciales si quieres, como "fechaCreacion", "fotoPerfilUrl", etc.
        )

        // Navegamos a la colección "users" y creamos un documento con el UID del usuario
        db.collection("users").document(userId)
            .set(userMap) // .set() crea o sobrescribe el documento
            .addOnSuccessListener {
                // Si resulta
                Log.d("Firestore", "Documento de usuario creado con éxito para el ID: $userId")

                // Navegamos a la pantalla principal
                goToMainActivity()
            }
            .addOnFailureListener { e ->
                // Si falla la creación del documento en Firestore...
                Log.w("Firestore", "Error al escribir el documento del usuario", e)
                Toast.makeText(this, "Error al guardar los datos del usuario: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    // NUEVA FUNCIÓN para centralizar la navegación
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Cierra RegisterActivity
    }
}
