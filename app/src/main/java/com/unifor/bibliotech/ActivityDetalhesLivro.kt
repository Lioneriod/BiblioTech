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

    private var livroId: String = ""
    private var usuarioId: String = ""
    private var tituloLivro: String = ""
    private var autorLivro: String = ""

    private lateinit var btnReservar: Button
    private lateinit var tvStatus: TextView
    private lateinit var tvTitulo: TextView
    private lateinit var tvDetalhes: TextView
    private lateinit var tvSinopse: TextView
    private lateinit var btnVoltar: ImageButton
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
        btnVoltar = findViewById(R.id.btnVoltar)
        tvTitulo = findViewById(R.id.tvLivroTitulo)
        tvDetalhes = findViewById(R.id.tvLivroDetalhes)
        tvStatus = findViewById(R.id.tvLivroStatus)
        tvSinopse = findViewById(R.id.tvSinopse)
        btnReservar = findViewById(R.id.btnReservar)

        btnReservar.isEnabled = false
        btnReservar.text = "Carregando..."

        livroId = intent.getStringExtra("LIVRO_ID") ?: ""
        usuarioId = intent.getStringExtra("USUARIO_ID") ?: ""

        btnVoltar.setOnClickListener {
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

        if (livroId != null) {
            carregarDadosDoLivro(livroId)
        } else {
            Toast.makeText(this, "Erro: nenhum livro selecionado.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun carregarDadosDoLivro(id: String) {
        fb.collection("livros").document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    tituloLivro = document.getString("titulo") ?: "Título"
                    autorLivro = document.getString("autor") ?: "Autor"
                    val sinopse = document.getString("sinopse") ?: "Sinopse"
                    val anoPub = document.getString("anoPub") ?: "--"
                    val status = document.get("status")
                    val idDisponivel = when (status) {
                        is Boolean -> status
                        is String -> status.lowercase() == "Disponível"
                        else -> false
                    }

                    tvTitulo.text = tituloLivro
                    tvSinopse.text = sinopse
                    tvDetalhes.text = "$autorLivro | Ano: $anoPub"

                    if (idDisponivel) {
                        tvStatus.text = "Disponível"
                        tvStatus.setBackgroundResource(R.drawable.bg_status_disponivel)
                        btnReservar.isEnabled = true
                        btnReservar.text = "Reservar Livro"
                    } else {
                        tvStatus.text = "Indisponível"
                        tvStatus.setBackgroundResource(R.drawable.bg_status_indisponivel)
                        btnReservar.isEnabled = false
                        btnReservar.text = "Reserva Indisponível"
                    }
                } else {
                    Toast.makeText(this, "Livro não encontrado", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar: ${e.message}", Toast.LENGTH_SHORT).show()
                btnReservar.text = "Erro de conexão"
            }
    }

    private fun realizarReserva() {
        val dataAtual = Timestamp.now()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 10)
        val dataPrazo = Timestamp(calendar.time)

        val novoEmprestimo = hashMapOf(
            "livroId" to livroId,
            "usuarioId" to usuarioId,
            "autor" to autorLivro,
            "prazo" to dataPrazo,
            "titulo" to tituloLivro,
            "usuarioId" to usuarioId,
            "livroId" to livroId,
            "dataEmprestimo" to dataAtual,
            "statusDevolucao" to "pendente"
        )

        val atualizacaoLivro = hashMapOf<String, Any>(
            "status" to false,
            "usuarioIdAtual" to usuarioId
        )

        val novaNotificacao = hashMapOf(
            "usuarioId" to usuarioId,
            "titulo" to "Empréstimo realizado com sucesso!",
            "corpo" to "Você pegou o livro \"$tituloLivro\". O prazo de devolução é ${formatarData(dataPrazo)}.",
            "timestamp" to dataAtual,
            "lida" to false
        )
        val batch = fb.batch()

        val emprestimoRef = fb.collection("emprestimos").document()
        batch.set(emprestimoRef, novoEmprestimo)

        val livroRef = fb.collection("livros").document(livroId)
        batch.update(livroRef, atualizacaoLivro)

        val notificacaoRef = fb.collection("notificacoes").document()
        batch.set(notificacaoRef, novaNotificacao)
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
    private fun formatarData(timestamp: Timestamp?): String {
        if (timestamp == null) return "Data não definida"
        try {
            val data = timestamp.toDate()
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            return sdf.format(data)
        } catch (e: Exception) {
            return "Data inválida"
        }
    }
    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}