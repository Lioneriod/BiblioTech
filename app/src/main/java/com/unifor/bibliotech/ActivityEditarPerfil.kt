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

class ActivityEditarPerfil : BaseActivity() {
    lateinit var fb: FirebaseFirestore
    private lateinit var usuarioId: String
    private lateinit var etNome: EditText
    private lateinit var etEmail: EditText
    private lateinit var etSenha: EditText
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
        etSenha = findViewById(R.id.etSenha)
        btnSalvar = findViewById(R.id.btnSalvar)
        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        voltar.setOnClickListener {
            finish()
            triggerHapticFeedback(this)
        }

        
        btnSalvar.setOnClickListener {
            salvarDados()
            triggerHapticFeedback(this)
        }

        
//        carregarDadosParaEdicao()
    }

//    private fun carregarDadosParaEdicao() {
//        fb.collection("usuario").document(usuarioId)
//            .get()
//            .addOnSuccessListener { document ->
//                if (document != null && document.exists()) {
//
//                    etNome.setText(document.getString("nome"))
//                    etEmail.setText(document.getString("email"))
//                }
//            }
//            .addOnFailureListener { e ->
//                Log.w("EDITARPERFIL", "Erro ao buscar documento", e)
//                Toast.makeText(this, "Falha ao carregar dados.", Toast.LENGTH_SHORT).show()
//            }
//    }

    private fun salvarDados() {
        val novoNome = etNome.text.toString().trim()
        val novoEmail = etEmail.text.toString().trim()
        val novaSenha = etSenha.text.toString()

        
        if (novoNome.isEmpty() && novoEmail.isEmpty() && novaSenha.isEmpty()) {
            Toast.makeText(this, "Por favor, Preencha ao menos um campo para salvar.", Toast.LENGTH_SHORT).show()
            return
        }

        val dadosAtualizados = hashMapOf<String, Any>()

        if (novoNome.isNotEmpty()) {
            dadosAtualizados["nome"] = novoNome
        }

        if (novoEmail.isNotEmpty()) {
            dadosAtualizados["email"] = novoEmail
        }

        if (novaSenha.isNotEmpty()) {
            dadosAtualizados["senha"] = novaSenha
        }

        btnSalvar.isEnabled = false 
        btnSalvar.text = "Salvando..."

        
        fb.collection("usuario")
            .whereEqualTo("id", usuarioId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documento = querySnapshot.documents[0]

                documento.reference.update(dadosAtualizados)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Erro ao atualizar: ${e.message}", Toast.LENGTH_SHORT).show()
                        btnSalvar.isEnabled = true
                        btnSalvar.text = "Salvar"
                    }
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