package com.example.tutorialreto

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tutorialreto.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var token: String? = null

    // SharedPreferences
    private val prefs by lazy {
        getSharedPreferences("Sesion", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar el token guardado (si existe)
        token = prefs.getString("TOKEN", null)

        binding.btn.setOnClickListener {

            val usuario = binding.edtNom.text.toString()
            val clave = binding.edtCon.text.toString()

            
            
            hacerLogin(usuario, clave)
            hacerLogin("emilys", "emilyspass")
        }
    }

    // hola
    // ---------- PASO A: LOGIN ----------
    private fun hacerLogin(usuario: String, clave: String) {

        lifecycleScope.launch {

            try {

                val resp = RetrofitClient.api.login(
                    LoginRequest(usuario, clave)
                )

                if (resp.isSuccessful) {

                    token = resp.body()?.accessToken

                    // Guardar el token en SharedPreferences
                    prefs.edit()
                        .putString("TOKEN", token)
                        .apply()

                    Log.d("API", "Token guardado: $token")
                    Toast.makeText(this@MainActivity, "Login exitoso", Toast.LENGTH_SHORT).show()

                    obtenerUsuario()

                } else {

                    Log.e("API", "Login falló: ${resp.code()}")
                    Toast.makeText(this@MainActivity, "Login falló: Credenciales incorrectas", Toast.LENGTH_SHORT).show()

                }

            } catch (e: Exception) {

                Log.e("API", "Error de red: ${e.message}")
                Toast.makeText(this@MainActivity, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // ---------- PASO B: GET PROTEGIDO ----------
    private fun obtenerUsuario() {

        val t = token ?: return

        lifecycleScope.launch {

            try {

                val resp = RetrofitClient.api.getCurrentUser("Bearer $t")

                if (resp.isSuccessful) {

                    val user = resp.body()

                    Log.d("API", "Hola ${user?.firstName}")
                    Log.d("API", "Email: ${user?.email}")
                    Toast.makeText(this@MainActivity, "Bienvenido ${user?.firstName}", Toast.LENGTH_SHORT).show()

                } else {

                    Log.e("API", "Error al obtener usuario: ${resp.code()}")
                    Toast.makeText(this@MainActivity, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()

                }

            } catch (e: Exception) {

                Log.e("API", "Error: ${e.message}")
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }
}