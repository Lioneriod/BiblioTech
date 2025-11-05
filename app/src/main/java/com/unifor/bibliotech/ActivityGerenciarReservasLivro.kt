package com.unifor.bibliotech

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityGerenciarReservasLivro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gerenciar_reservas_livro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaGerenciarReservasLivro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        val dadosReservas = listOf(
            Reserva(
                titulo = "Five Night's At Freddy's: Into The Pit",
                nomeAluno = "Fabricio Machado",
                dataSolic = "23/10/2025"
            ),
            Reserva(
                titulo = "O pequeno príncipe",
                nomeAluno = "Gabriela Castelo",
                dataSolic = "15/11/2025"
            ),
            Reserva(
                titulo = "Coraline",
                nomeAluno = "Ruan",
                dataSolic = "22/08/2025"
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvReservasPendentes)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ReservaAdapter(dadosReservas)

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