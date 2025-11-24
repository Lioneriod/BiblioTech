package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityBuscaLivros : AppCompatActivity() {
    private lateinit var fb: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var etBusca: EditText
    private lateinit var livroAdapter: LivroAdapter
    private var listaCompletaDeLivros: MutableList<Livro> = mutableListOf()
    private lateinit var usuarioId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_busca_livros)
        fb = Firebase.firestore
        usuarioId = intent.getStringExtra("USUARIO_ID") ?: ""

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaBuscarLivros)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        val btnSurpreendaMe: ImageButton = findViewById(R.id.btnSurpreendaMe)
        recyclerView = findViewById(R.id.rvResultadosBusca)
        etBusca = findViewById(R.id.etBusca)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val onItemClick: (Livro) -> Unit = { livroClicado ->
            val intent = Intent(this, ActivityDetalhesLivro::class.java)
            intent.putExtra("LIVRO_ID", livroClicado.id)
            intent.putExtra("USUARIO_ID", usuarioId)
            intent.putExtra("TITULO_LIVRO", livroClicado.titulo)
            intent.putExtra("AUTOR_LIVRO", livroClicado.autor)
            intent.putExtra("SINOPSE_LIVRO", livroClicado.sinopse)
            intent.putExtra("ANO_PUB_LIVRO", livroClicado.anoPub)
            intent.putExtra("STATUS_LIVRO", livroClicado.status)
            startActivity(intent)
        }

        btnSurpreendaMe.setOnClickListener {
            buscarLivroAleatorio()
        }

        livroAdapter = LivroAdapter(listaCompletaDeLivros, onItemClick)
        recyclerView.adapter = livroAdapter

        etBusca.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    livroAdapter.filter(it.toString())
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        voltar.setOnClickListener {
            finish()
        }
        carregarLivrosDoFirebase()
    }

    private fun buscarLivroAleatorio() {
        val collectionRef = fb.collection("livros")

        val randomId = collectionRef.document().id

        collectionRef
            .whereGreaterThanOrEqualTo(FieldPath.documentId(), randomId)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val livro = documents.documents[0]
                    abrirDetalhesLivro(livro.id)
                } else {
                    collectionRef
                        .whereLessThan(FieldPath.documentId(), randomId)
                        .limit(1)
                        .get()
                        .addOnSuccessListener { docsWrap ->
                            if (!docsWrap.isEmpty) {
                                val livro = docsWrap.documents[0]
                                abrirDetalhesLivro(livro.id)
                            } else {
                                Toast.makeText(this, "Nenhum livro encontrado", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao sortear: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun abrirDetalhesLivro(livroId: String) {
        val intent = Intent(this, ActivityDetalhesLivro::class.java)
        intent.putExtra("LIVRO_ID", livroId)
        startActivity(intent)
        Toast.makeText(this, "Livro Sorteado ID: $livroId", Toast.LENGTH_LONG).show()
    }

    private fun carregarLivrosDoFirebase() {
        fb.collection("livros")
            .get()
            .addOnSuccessListener { querySnapshot ->
                listaCompletaDeLivros.clear()
                val livrosDoFirebase = querySnapshot.toObjects(Livro::class.java)
                listaCompletaDeLivros.addAll(livrosDoFirebase)
                livroAdapter.filter(etBusca.text.toString())
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar livros.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}