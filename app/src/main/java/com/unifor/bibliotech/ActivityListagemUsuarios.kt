package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityListagemUsuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listagem_usuarios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaListagemUsuarios)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        val dadosUsuarios = listOf(
            Usuario(
                nome = "João Lucas Lobo Pinto Barboza",
                detalhes = "Matrícula: 2412830 - usuário desde: 29/10/2025"
            ),
            Usuario(
                nome = "Carlinhos",
                detalhes = "Matrícula: 3143133 - usuário desde: 23/11/2025"
            ),
            Usuario(
                nome = "George Maldivas",
                detalhes = "Matrícula: 8433763 - usuário desde: 05/09/2025"
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvUsuarios)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val detailClicked: (Usuario) -> Unit = { detalhesClidado ->
            val intent = Intent(this, ActivityHistoricoUsuario::class.java)

            intent.putExtra("USER_NAME", detalhesClidado.nome)
            intent.putExtra("USER_DETAILS", detalhesClidado.detalhes)

            startActivity(intent)
        }

        val onRemoveClicked: (Usuario) -> Unit = { detalhesClidado ->
            TODO()
        }

        recyclerView.adapter = UsuarioAdapter(dadosUsuarios, detailClicked, onRemoveClicked)

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