package com.unifor.bibliotech

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class ActivityHistoricoUsuario : AppCompatActivity() {

    private lateinit var fb: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoricoUsuarioAdapter
    private val listaDeHistorico: MutableList<HistoricoUsuario> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historico_usuario)

        fb = Firebase.firestore

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaHistoricoUsuarios)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nomeUsuario = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")
        val tvnomeUsuario: TextView = findViewById(R.id.tvNomeUsuario)
        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        tvnomeUsuario.text = "Histórico de atividades de: ${nomeUsuario}"
        setupRecyclerView()

        if (userId != null && userId.isNotEmpty()) {
            carregarHistoricoDoFirebase(userId)
        } else {
            Toast.makeText(this, "Erro: ID do usuário não encontrado.", Toast.LENGTH_LONG).show()
            Log.e("FIREHISTORICO", "USER_ID está nulo ou vazio.")
        }
        voltar.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rvHistorico)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoricoUsuarioAdapter(listaDeHistorico)
        recyclerView.adapter = adapter
    }

    private fun carregarHistoricoDoFirebase(userId: String) {
        fb.collection("emprestimos")
            .whereEqualTo("usuarioId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Toast.makeText(this, "Nenhum histórico encontrado.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                listaDeHistorico.clear()
                for (document in querySnapshot.documents) {
                    val titulo = document.getString("titulo") ?: "Título desconhecido"
                    val prazoTimestamp = document.getTimestamp("prazo")
                    val historicoItem = HistoricoUsuario(
                        tipoAcao = "Empréstimo",
                        data = "Ativo",
                        tituloLivro = titulo,
                        statusDetalhe = "Prazo de devolução: ${formatarData(prazoTimestamp)}"
                    )
                    listaDeHistorico.add(historicoItem)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w("FIREHISTORICO", "Erro ao carregar histórico", e)
                Toast.makeText(this, "Erro ao carregar histórico.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun formatarData(timestamp: Timestamp?): String {
        if (timestamp == null) return "Data não definida"
        try {
            val data = timestamp.toDate()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return sdf.format(data)
        } catch (e: Exception) {
            Log.e("FORMATARDATA", "Erro ao formatar data", e)
            return "Data inválida"
        }
    }

    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}