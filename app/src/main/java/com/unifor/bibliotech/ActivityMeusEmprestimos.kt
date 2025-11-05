package com.unifor.bibliotech

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityMeusEmprestimos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meus_emprestimos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaEmprestimos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        val dadosEmprestimo = listOf(
            Emprestimo(
                titulo = "Five Night's At Freddy's: Into The Pit",
                autor = "Scott Cawthon",
                prazo = "15/10"
            ),
            Emprestimo(
                titulo = "Coraline",
                autor = "Adilene",
                prazo = "05/09"
            ),
            Emprestimo(
                titulo = "Five Night's At Freddy's: The Fourth Closet",
                autor = "Scott Cawthon",
                prazo = "20/12"
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvEmprestimos)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = EmprestimoAdapter(dadosEmprestimo)

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