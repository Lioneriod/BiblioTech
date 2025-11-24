package com.unifor.bibliotech

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityRedefinirSenha : AppCompatActivity() {

    private lateinit var fb: FirebaseFirestore
    private lateinit var userId: String

    private lateinit var etPin: EditText
    private lateinit var etNovaSenha: EditText
    private lateinit var etConfirmarSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redefinir_senha) // Crie este layout
        fb = Firebase.firestore
        userId = intent.getStringExtra("USER_ID") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(this, "Erro de processamento.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        etPin = findViewById(R.id.etPinDigitado)
        etNovaSenha = findViewById(R.id.etNovaSenha)
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha)
        val btnRedefinir: AppCompatButton = findViewById(R.id.btnRedefinir)

        btnRedefinir.setOnClickListener {
            verificarPinEAtualizar()
        }
    }

    private fun verificarPinEAtualizar() {
        val pinDigitado = etPin.text.toString().trim()
        val novaSenha = etNovaSenha.text.toString()
        val confirmarSenha = etConfirmarSenha.text.toString()

        if (pinDigitado.isEmpty() || novaSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (novaSenha != confirmarSenha) {
            Toast.makeText(this, "As senhas não conferem.", Toast.LENGTH_SHORT).show()
            return
        }

        fb.collection("usuario").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val pinDoBanco = document.getString("resetPin")

                    if (pinDoBanco == pinDigitado) {
                        atualizarSenha(novaSenha)
                    } else {
                        Toast.makeText(this, "PIN incorreto.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao validar PIN.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun atualizarSenha(novaSenha: String) {
        val updates = mapOf(
            "senha" to novaSenha,
            "resetPin" to FieldValue.delete()
        )

        fb.collection("usuario").document(userId)
            .update(updates)
            .addOnSuccessListener {
                Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao atualizar senha.", Toast.LENGTH_SHORT).show()
            }
    }
}