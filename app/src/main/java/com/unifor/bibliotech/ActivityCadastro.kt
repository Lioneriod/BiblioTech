package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
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
    private lateinit var switchAdmin: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        fb = Firebase.firestore
        etNome = findViewById(R.id.etNome)
        etEmail = findViewById(R.id.etEmail)
        etSenha = findViewById(R.id.etSenha)
        switchAdmin = findViewById(R.id.switchAdmin)
        val btnCadastro: TextView = findViewById(R.id.btnCadastrar)
        val btnLogar: TextView = findViewById(R.id.btnLogin)

        btnCadastro.setOnClickListener {
            cadastrarUsuario()
        }

        btnLogar.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }
    }
    private fun cadastrarUsuario() {
        val nome = etNome.text.toString()
        val email = etEmail.text.toString()
        val senha = etSenha.text.toString()
        val tipoUsuario = if (switchAdmin.isChecked) "admin" else "aluno"
        val newDocRef = fb.collection("usuario").document()
        val idUsuario = newDocRef.id
        val usuarioMap = mapOf(
            "id" to idUsuario,
            "nome" to nome,
            "email" to email,
            "senha" to senha,
            "tipo" to tipoUsuario
        )

        newDocRef.set(usuarioMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Cadastro de $tipoUsuario realizado com sucesso!", Toast.LENGTH_SHORT).show()
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