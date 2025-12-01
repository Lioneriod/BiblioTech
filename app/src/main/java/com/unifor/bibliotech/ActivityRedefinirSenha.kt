package com.unifor.bibliotech

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityRedefinirSenha : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val configuration = Configuration(newBase.resources.configuration)

        configuration.fontScale = 1.0f

        val context = ContextWrapper(newBase.createConfigurationContext(configuration))
        super.attachBaseContext(context)
    }

    private lateinit var fb: FirebaseFirestore
    private lateinit var userId: String

    private lateinit var etPin: EditText
    private lateinit var etNovaSenha: EditText
    private lateinit var etConfirmarSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_redefinir_senha)
        fb = Firebase.firestore

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaRedefinirSenha)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userId = intent.getStringExtra("USER_ID") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(this, "Erro: Usuário não identificado.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        etPin = findViewById(R.id.etPin)
        etNovaSenha = findViewById(R.id.etNovaSenha)
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha)
        val btnRedefinir: AppCompatButton = findViewById(R.id.btnRedefinirSenha)
        val tvCancelar: TextView = findViewById(R.id.tvCancelar)

        tvCancelar.setOnClickListener {
            finish()
        }

        btnRedefinir.setOnClickListener {
            validarEAtualizarSenha()
        }
    }

    private fun validarEAtualizarSenha() {
        val pinDigitado = etPin.text.toString().trim()
        val novaSenha = etNovaSenha.text.toString()
        val confirmarSenha = etConfirmarSenha.text.toString()

        if (pinDigitado.isEmpty() || novaSenha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (novaSenha != confirmarSenha) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
            return
        }

        fb.collection("usuario").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val pinDoBanco = document.getString("resetPin")

                    if (pinDoBanco == pinDigitado) {
                        atualizarSenhaNoFirebase(novaSenha)
                    } else {
                        Toast.makeText(this, "PIN incorreto.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao validar dados.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun atualizarSenhaNoFirebase(novaSenha: String) {
        val updates = mapOf(
            "senha" to novaSenha,
            "resetPin" to FieldValue.delete()
        )

        fb.collection("usuario").document(userId)
            .update(updates)
            .addOnSuccessListener {
                Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_LONG).show()

                val intent = Intent(this, ActivityLogin::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar nova senha.", Toast.LENGTH_SHORT).show()
            }
    }
}