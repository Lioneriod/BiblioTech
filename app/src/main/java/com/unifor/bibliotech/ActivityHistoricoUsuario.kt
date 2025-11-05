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

class ActivityHistoricoUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historico_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaHistoricoUsuarios)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nome = intent.getStringExtra("USER_NAME")

        val tvnomeUsuario: TextView = findViewById(R.id.tvNomeUsuario)
        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        tvnomeUsuario.text = "Histórico de atividades de: ${nome}"

        val dadosHistorico = listOf(
            HistoricoUsuario(
                tipoAcao = "Empréstimo",
                data = "20/10/2025",
                tituloLivro = "Five Night's At Freddy's: Into The Pit",
                statusDetalhe = "Devolvido com 2 dias de atraso"
            ),
            HistoricoUsuario(
                tipoAcao = "Empréstimo",
                data = "20/10/2025",
                tituloLivro = "Five Night's At Freddy's: The Fourth Closet",
                statusDetalhe = "Devolvido antes da data de expiração"
            ),
            HistoricoUsuario(
                tipoAcao = "Empréstimo",
                data = "20/10/2025",
                tituloLivro = "Five Night's At Freddy's: Silver Eyes",
                statusDetalhe = "Devolvido no dia"
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvHistorico)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistoricoUsuarioAdapter(dadosHistorico)

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