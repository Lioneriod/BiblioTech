package com.unifor.bibliotech

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
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

class ActivityMeusEmprestimos : AppCompatActivity() {
    private lateinit var fb: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmprestimoAdapter
    private var listaEmprestimos: MutableList<Emprestimo> = mutableListOf()
    private lateinit var usuarioId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meus_emprestimos)
        fb = Firebase.firestore
        usuarioId = intent.getStringExtra("USUARIO_ID") ?: ""

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaEmprestimos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        recyclerView = findViewById(R.id.rvEmprestimos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmprestimoAdapter(listaEmprestimos)
        recyclerView.adapter = adapter

        voltar.setOnClickListener {
            finish()
        }

        if (usuarioId.isNotEmpty()) {
            carregarMeusEmprestimos()
        } else {
            Toast.makeText(this, "Erro: Usuário não identificado.", Toast.LENGTH_SHORT).show()
        }

    }
    private fun carregarMeusEmprestimos() {
        fb.collection("emprestimos")
            .whereEqualTo("usuarioId", usuarioId)
            .orderBy("dataEmprestimo", Query.Direction.DESCENDING) // Mais novos primeiro
            .get()
            .addOnSuccessListener { querySnapshot ->
                listaEmprestimos.clear()
                listaEmprestimos.addAll(querySnapshot.toObjects(Emprestimo::class.java))
                adapter.notifyDataSetChanged()
                if (listaEmprestimos.isEmpty()) {
                    Toast.makeText(this, "Você não possui empréstimos.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar empréstimos: ${e.message}", Toast.LENGTH_SHORT).show()
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