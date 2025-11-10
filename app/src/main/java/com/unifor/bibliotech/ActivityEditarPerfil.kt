package com.unifor.bibliotech

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityEditarPerfil : AppCompatActivity() {
    private lateinit var fb: FirebaseFirestore
    private lateinit var usuarioId: String
    private lateinit var etNome: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_perfil)
        fb = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaEditarPerfil)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usuarioId = intent.getStringExtra("USUARIO_ID") ?: ""
        if (usuarioId.isEmpty()) {
            Log.e("EDITARPERFIL", "Erro: USUARIO_ID está vazio.")
            Toast.makeText(this, "Erro ao carregar perfil.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        etNome = findViewById(R.id.etNome)
        etEmail = findViewById(R.id.etEmail)
        btnSalvar = findViewById(R.id.btnSalvar)
        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        voltar.setOnClickListener {
            finish()
        }

        
        btnSalvar.setOnClickListener {
            salvarDados()
        }

        
        carregarDadosParaEdicao()
    }

    private fun carregarDadosParaEdicao() {
        fb.collection("usuario").document(usuarioId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    
                    etNome.setText(document.getString("nome"))
                    etEmail.setText(document.getString("email"))
                }
            }
            .addOnFailureListener { e ->
                Log.w("EDITARPERFIL", "Erro ao buscar documento", e)
                Toast.makeText(this, "Falha ao carregar dados.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun salvarDados() {
        val novoNome = etNome.text.toString().trim()
        val novoEmail = etEmail.text.toString().trim()

        
        if (novoNome.isEmpty() || novoEmail.isEmpty()) {
            Toast.makeText(this, "Nome e E-mail não podem ficar em branco.", Toast.LENGTH_SHORT).show()
            return
        }

        
        val dadosAtualizados = mapOf(
            "nome" to novoNome,
            "email" to novoEmail
        )

        btnSalvar.isEnabled = false 
        btnSalvar.text = "Salvando..."

        
        fb.collection("usuario").document(usuarioId)
            .update(dadosAtualizados)
            .addOnSuccessListener {
                Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish() 
            }
            .addOnFailureListener { e ->
                Log.w("EDITARPERFIL", "Erro ao atualizar documento", e)
                Toast.makeText(this, "Falha ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
                btnSalvar.isEnabled = true 
                btnSalvar.text = "Salvar"
            }
    }

    
    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}