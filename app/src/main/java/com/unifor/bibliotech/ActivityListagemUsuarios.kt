package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.adapters.UsuarioAdapter
import com.unifor.bibliotech.datas.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityListagemUsuarios : AppCompatActivity() {
    private lateinit var fb: FirebaseFirestore
    private lateinit var etBuscaUsuarios: EditText
    private lateinit var recyclerView: RecyclerView
    private var listaDeUsuarios: MutableList<Usuario> = mutableListOf()
    private lateinit var adapter: UsuarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listagem_usuarios)
        fb = Firebase.firestore

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaListagemUsuarios)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        etBuscaUsuarios = findViewById(R.id.etBuscaUsuario)

        voltar.setOnClickListener {
            finish()
        }

        etBuscaUsuarios.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    adapter.filter(it.toString())
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        setupRecyclerView()

        carregarUsuariosDoFirebase()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rvUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val detailClicked: (Usuario) -> Unit = { usuarioClicado ->
            val intent = Intent(this, ActivityHistoricoUsuario::class.java)

            intent.putExtra("USER_ID", usuarioClicado.id)
            intent.putExtra("USER_NAME", usuarioClicado.nome)

            startActivity(intent)
        }

        val onRemoveClicked: (Usuario) -> Unit = { usuarioParaRemover ->
            removerUsuario(usuarioParaRemover)
        }

        adapter = UsuarioAdapter(listaDeUsuarios, detailClicked, onRemoveClicked)
        recyclerView.adapter = adapter
    }

    private fun carregarUsuariosDoFirebase() {
        fb.collection("usuario")
            .whereNotEqualTo("tipo", "admin")
            .get()
            .addOnSuccessListener { querySnapshot ->

                listaDeUsuarios.clear()

                for (document in querySnapshot.documents) {
                    val id = document.id
                    val nome = document.getString("nome") ?: "Nome não encontrado"
                    val email = document.getString("email") ?: ""
                    val detalhes = "Email: $email"

                    listaDeUsuarios.add(Usuario(id = id, nome = nome, detalhes = detalhes))
                }
                adapter.filter(etBuscaUsuarios.text.toString())

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                
                Toast.makeText(this, "Erro ao carregar usuários.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun removerUsuario(usuario: Usuario) {
        fb.collection("usuario").document(usuario.id)
            .delete()
            .addOnSuccessListener {
                
                Toast.makeText(this, "Usuário removido.", Toast.LENGTH_SHORT).show()
                listaDeUsuarios.remove(usuario)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                
                Toast.makeText(this, "Erro ao remover usuário.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onPause() { super.onPause() }
    override fun onResume() {
        super.onResume()
        carregarUsuariosDoFirebase()
    }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}