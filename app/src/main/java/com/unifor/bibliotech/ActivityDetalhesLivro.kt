package com.unifor.bibliotech

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class ActivityDetalhesLivro : AppCompatActivity() {
    private lateinit var fb: FirebaseFirestore
    private lateinit var livroId: String
    private lateinit var usuarioId: String 
    private lateinit var titulo: String
    private lateinit var autor: String
    private lateinit var btnReservar: Button
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes_livro)
        fb = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaDetalhesLivro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        livroId = intent.getStringExtra("LIVRO_ID") ?: ""
        usuarioId = intent.getStringExtra("USUARIO_ID") ?: ""
        titulo = intent.getStringExtra("TITULO_LIVRO") ?: "Título"
        autor = intent.getStringExtra("AUTOR_LIVRO") ?: "Autor"

        val anoPub = intent.getStringExtra("ANO_PUB_LIVRO") ?: "Ano"
        val statusDisponivel = intent.getBooleanExtra("STATUS_LIVRO", false)
        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        val tvTitulo: TextView = findViewById(R.id.tvLivroTitulo)
        val tvDetalhes: TextView = findViewById(R.id.tvLivroDetalhes)

        tvStatus = findViewById(R.id.tvLivroStatus)
        btnReservar = findViewById(R.id.btnReservar)

        val tvSinopse: TextView = findViewById(R.id.tvSinopse)

        tvTitulo.text = titulo
        tvDetalhes.text = "$autor | Ano: $anoPub"
        tvSinopse.text = "teste"
        if (statusDisponivel) {
            tvStatus.text = "DISPONÍVEL"
            btnReservar.isEnabled = true
        } else {
            tvStatus.text = "Indisponível"
            btnReservar.isEnabled = false
            btnReservar.text = "Indisponível para Emprestimo"
        }
        voltar.setOnClickListener {
            finish()
        }

        btnReservar.setOnClickListener {
            if (livroId.isEmpty() || usuarioId.isEmpty()) {
                Toast.makeText(this, "Erro: ID do livro ou usuário não encontrado.", Toast.LENGTH_LONG).show()
                Log.e("FIRERESERVA", "LivroID ($livroId) ou UsuarioID ($usuarioId) está vazio.")
                return@setOnClickListener
            }
            btnReservar.isEnabled = false
            btnReservar.text = "Reservando..."
            realizarReserva()
        }
    }

    private fun realizarReserva() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 10)
        val dataPrazo = Timestamp(calendar.time)
        val novoEmprestimo = mapOf(
            "autor" to autor,
            "prazo" to dataPrazo,
            "titulo" to titulo,
            "usuarioId" to usuarioId,
            "livroId" to livroId 
        )

        val batch = fb.batch()
        val emprestimoRef = fb.collection("emprestimos").document()
        batch.set(emprestimoRef, novoEmprestimo)
        val livroRef = fb.collection("livros").document(livroId)
        batch.update(livroRef, "status", false)
        batch.commit()
            .addOnSuccessListener {
                Toast.makeText(this, "Livro reservado com sucesso!", Toast.LENGTH_LONG).show()
                tvStatus.text = "Indisponível"
                btnReservar.text = "Indisponível para Emprestimo"
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao reservar: ${e.message}", Toast.LENGTH_LONG).show()
                btnReservar.isEnabled = true
                btnReservar.text = "Reservar"
            }
    }

    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}