package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityLogin : AppCompatActivity() {
    lateinit var fb: FirebaseFirestore;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        fb = Firebase.firestore;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaLogin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val linkCadastrar: TextView = findViewById(R.id.btnCadastreSe)
        val linkEsqueceuSenha: TextView = findViewById(R.id.btnEsqueceuSenha)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etSenha: EditText = findViewById(R.id.etSenha)
        val logar: AppCompatButton = findViewById(R.id.btnEntrar)

        linkCadastrar.setOnClickListener {
            val intent = Intent(this, ActivityCadastro::class.java)
            startActivity(intent)
        }

        linkEsqueceuSenha.setOnClickListener {
            val intent = Intent(this, ActivityEsqueceuSenha::class.java)
            startActivity(intent)
        }

        logar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val senha = etSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha E-mail e Senha.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            fb.collection("usuario")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        Toast.makeText(this, "E-mail não cadastrado.", Toast.LENGTH_SHORT).show()
                    } else {
                        val document = querySnapshot.documents[0]
                        val senhaDoBanco = document.getString("senha")

                        if (senhaDoBanco == senha) {
                            Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                            val tipoUsuario = document.getString("tipo")

                            if (tipoUsuario == "admin") {
                                val nomeDoAdmin = document.getString("nome")
                                val idDoAdmin = document.id
                                val intent = Intent(this, ActivityHomeAdmin::class.java)
                                intent.putExtra("ADMIN_ID", idDoAdmin)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                val nomeDoUsuario = document.getString("nome")
                                val idDoUsuario = document.id
                                val intent = Intent(this, ActivityHomeUsuario::class.java)
                                intent.putExtra("NOME_USUARIO", nomeDoUsuario)
                                intent.putExtra("USUARIO_ID", idDoUsuario)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this, "Senha incorreta.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("FIRELOGIN", "Erro ao fazer login", e)
                    Toast.makeText(this, "Erro ao tentar logar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }
}