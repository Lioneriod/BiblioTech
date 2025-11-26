package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class ActivityEsqueceuSenha : AppCompatActivity() {

    private lateinit var fb: FirebaseFirestore
    private lateinit var etEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueceu_senha)
        fb = Firebase.firestore

//        etEmail = findViewById(R.id.etEmailRecuperacao)
//        val btnEnviar: AppCompatButton = findViewById(R.id.btnEnviarPin)
        val tvVoltarLogin: TextView = findViewById(R.id.tvVoltarLogin)

        tvVoltarLogin.setOnClickListener { finish() }

//        btnEnviar.setOnClickListener {
//            val email = etEmail.text.toString().trim()
//            if (email.isNotEmpty()) {
//                enviarPinRecuperacao(email)
//            } else {
//                Toast.makeText(this, "Digite seu e-mail.", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

//    private fun enviarPinRecuperacao(email: String) {
//        fb.collection("usuario")
//            .whereEqualTo("email", email)
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                if (querySnapshot.isEmpty) {
//                    Toast.makeText(this, "E-mail não encontrado.", Toast.LENGTH_SHORT).show()
//                } else {
//                    val document = querySnapshot.documents[0]
//                    val userId = document.id
//                    val pinGerado = Random.nextInt(1000, 9999).toString()
//
//                    fb.collection("usuario").document(userId)
//                        .update("resetPin", pinGerado)
//                        .addOnSuccessListener {
//                            Log.d("ESQUECEU_SENHA", "O PIN enviado para o e-mail seria: $pinGerado")
//                            Toast.makeText(this, "PIN Enviado: $pinGerado", Toast.LENGTH_LONG).show()
//
//                            val intent = Intent(this, ActivityRedefinirSenha::class.java)
//                            intent.putExtra("USER_ID", userId)
//                            startActivity(intent)
//                            finish()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(this, "Erro ao gerar PIN.", Toast.LENGTH_SHORT).show()
//                        }
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Erro ao buscar usuário.", Toast.LENGTH_SHORT).show()
//            }
//    }
}