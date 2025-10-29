package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
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

            if(email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha E-mail e Senha.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(email == "aluno@gmail.com" && senha == "123321") {
                val intent = Intent(this, ActivityHomeUsuario::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else if(email == "admin@gmail.com" && senha == "123321") {
                val intent = Intent(this, ActivityHomeAdmin::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this, "E-mail ou Senha incorretos.", Toast.LENGTH_SHORT).show()
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