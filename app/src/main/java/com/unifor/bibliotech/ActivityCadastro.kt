package com.unifor.bibliotech

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityCadastro : AppCompatActivity() {
    lateinit var fb: FirebaseFirestore
    private lateinit var etNome: EditText
    private lateinit var etEmail: EditText
    private lateinit var etSenha: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        fb = Firebase.firestore
        etNome = findViewById(R.id.etNome)
        etEmail = findViewById(R.id.etEmail)
        etSenha = findViewById(R.id.etSenha)
        val btnCadastro: TextView = findViewById(R.id.btnCadastrar)

        btnCadastro.setOnClickListener {
            cadastrarUsuario()
        }
    }
    private fun cadastrarUsuario() {
        val nome = etNome.text.toString()
        val email = etEmail.text.toString()
        val senha = etSenha.text.toString()

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val usuarioMap = mapOf(
            "nome" to nome,
            "email" to email,
            "senha" to senha,
            "tipo" to "aluno"
        )

        fb.collection("usuario")
            .add(usuarioMap)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao realizar cadastro: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}