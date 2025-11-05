package com.unifor.bibliotech

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityDetalhesLivro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes_livro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaDetalhesLivro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titulo = intent.getStringExtra("TITULO_LIVRO")
        val autor = intent.getStringExtra("AUTOR_LIVRO")
        val anoPub = intent.getStringExtra("ANO_PUB_LIVRO")
        val statusDisponivel = intent.getBooleanExtra("STATUS_LIVRO", false)

        val voltar: ImageButton = findViewById(R.id.btnVoltar)
        val tvTitulo: TextView = findViewById(R.id.tvLivroTitulo)
        val tvDetalhes: TextView = findViewById(R.id.tvLivroDetalhes)
        val tvStatus: TextView = findViewById(R.id.tvLivroStatus)
        val btnReservar: Button = findViewById(R.id.btnReservar)
        val tvSinopse: TextView = findViewById(R.id.tvSinopse)

        tvTitulo.text = titulo
        tvDetalhes.text = "${autor} | Ano: ${anoPub}"
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