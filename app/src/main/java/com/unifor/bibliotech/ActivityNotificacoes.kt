package com.unifor.bibliotech

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.Toast

class ActivityNotificacoes : AppCompatActivity() {
    private lateinit var fb: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificacaoAdapter
    private var listaNotificacoes: MutableList<Notificacao> = mutableListOf()
    private lateinit var usuarioId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notificacoes)
        fb = Firebase.firestore
        usuarioId = intent.getStringExtra("USUARIO_ID") ?: ""

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaNotificacoes)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        recyclerView = findViewById(R.id.rvNotificacoes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotificacaoAdapter(listaNotificacoes)
        recyclerView.adapter = adapter

        voltar.setOnClickListener {
            finish()
        }

        if (usuarioId.isNotEmpty()) {
            carregarNotificacoes()
        } else {
            Toast.makeText(this, "Erro: Usuário não identificado.", Toast.LENGTH_SHORT).show()
        }

    }
    private fun carregarNotificacoes() {
        fb.collection("notificacoes")
            .whereEqualTo("usuarioId", usuarioId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                listaNotificacoes.clear()
                listaNotificacoes.addAll(querySnapshot.toObjects(Notificacao::class.java))
                adapter.notifyDataSetChanged()
                if (listaNotificacoes.isEmpty()) {
                    Toast.makeText(this, "Nenhuma notificação.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar notificações: ${e.message}", Toast.LENGTH_SHORT).show()
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