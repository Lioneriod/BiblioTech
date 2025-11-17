package com.unifor.bibliotech
import android.os.Bundle
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

class ActivityEmissaoRelatorio : AppCompatActivity() {
    private lateinit var fb: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emissao_relatorio)
        fb = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_main_root_relatorios)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        val btnGerar: AppCompatButton = findViewById(R.id.btnGerarRelatorio)
        val tvResultado: TextView = findViewById(R.id.tvResultadoRelatorio)
        voltar.setOnClickListener {
            finish()
        }

        btnGerar.setOnClickListener {
            gerarRelatorio(tvResultado, btnGerar)
        }
    }

    private fun gerarRelatorio(tvResultado: TextView, btnGerar: AppCompatButton) {
        btnGerar.isEnabled = false
        btnGerar.text = "Gerando..."
        tvResultado.text = "Calculando dados..."

        fb.collection("emprestimos")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    tvResultado.text = "Nenhum empréstimo encontrado."
                    btnGerar.isEnabled = true
                    btnGerar.text = "Gerar Relatório"
                    return@addOnSuccessListener
                }

                val contagemLivros = mutableMapOf<String, Int>()
                for (document in querySnapshot.documents) {
                    val titulo = document.getString("titulo") ?: "Sem Título"
                    val contagemAtual = contagemLivros.getOrDefault(titulo, 0)
                    contagemLivros[titulo] = contagemAtual + 1
                }

                val topLivros = contagemLivros.entries
                    .sortedByDescending { it.value }
                    .take(5)

                val resultadoString = StringBuilder("Top 5 Livros Mais Emprestados:\n")
                topLivros.forEachIndexed { index, entry ->
                    resultadoString.append("${index + 1}. ${entry.key} (${entry.value} empréstimos)\n")
                }

                tvResultado.text = resultadoString.toString()
                btnGerar.isEnabled = true
                btnGerar.text = "Gerar Relatório"

            }
            .addOnFailureListener { e ->
                tvResultado.text = "Erro ao gerar relatório: ${e.message}"
                btnGerar.isEnabled = true
                btnGerar.text = "Gerar Relatório"
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
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