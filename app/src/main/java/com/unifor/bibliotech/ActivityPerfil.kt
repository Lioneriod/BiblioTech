package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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

class ActivityPerfil : AppCompatActivity() {
    lateinit var fb: FirebaseFirestore
    private lateinit var usuarioId: String
    private lateinit var tvNomeAluno: TextView
    private lateinit var tvEmailAluno: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        fb = Firebase.firestore 

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaPerfilUsuario)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnacessibilidade: ImageButton = findViewById(R.id.btnAcessibilidade)

        
        usuarioId = intent.getStringExtra("USUARIO_ID") ?: ""
        if (usuarioId.isEmpty()) {
            Log.e("PERFIL", "Erro: USUARIO_ID está vazio.")
            Toast.makeText(this, "Erro ao carregar perfil.", Toast.LENGTH_SHORT).show()
            finish() 
            return
        }

        
        tvNomeAluno = findViewById(R.id.tvNomeAluno) 
        tvEmailAluno = findViewById(R.id.tvEmailAluno) 

        
        val logout: AppCompatButton = findViewById(R.id.btnLogout)
        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        val editarDados: TextView = findViewById(R.id.tvEditarDados)

        logout.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        voltar.setOnClickListener {
            finish()
        }

        editarDados.setOnClickListener {
            val intent = Intent(this, ActivityEditarPerfil::class.java)
            intent.putExtra("USUARIO_ID", usuarioId)
            startActivity(intent)
        }

        btnacessibilidade.setOnClickListener {
            val intent = Intent(this, ActivityAcessibilidade::class.java)
            startActivity(intent)
        }

        
        carregarDadosDoPerfil()
    }

    private fun carregarDadosDoPerfil() {
        if (usuarioId.isEmpty()) return 

        fb.collection("usuario")
            .whereEqualTo("id", usuarioId)
            .addSnapshotListener{ snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshots != null && !snapshots.isEmpty) {
                    val documento = snapshots.documents[0]

                    val nome = documento.getString("Nome") ?: "Aluno"
                    val email = documento.getString("email") ?: "aluno@gmail.com"

                    tvNomeAluno.text = nome
                    tvEmailAluno.text = email
                } else {
                    Log.d("PERFIL", "Nenhum documento encontrado.")
                }
            }
    }

    override fun onResume() {
        super.onResume()
        
        if (::usuarioId.isInitialized && usuarioId.isNotEmpty()) {
            carregarDadosDoPerfil()
        }
    }

    
    override fun onPause() { super.onPause() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}