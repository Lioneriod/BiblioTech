package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat

class ActivityHomeUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaHomeUsuario)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val notificacoes: ImageButton = findViewById(R.id.btnNotificacoes)
        val saudacao: TextView = findViewById(R.id.tvSaudacao)
        val perfil: ImageView = findViewById(R.id.ivFotoPerfil)
        val pesquisar: ConstraintLayout = findViewById(R.id.buscarLivros)
        val emprestimos: ConstraintLayout = findViewById(R.id.cardEmprestimos)
//        val reservas: ConstraintLayout = findViewById(R.id.cardReservas)

        notificacoes.setOnClickListener {
            val intent = Intent(this, ActivityNotificacoes::class.java)
            startActivity(intent)
        }

        perfil.setOnClickListener {
            val intent = Intent(this, ActivityPerfil::class.java)
            startActivity(intent)
        }

        pesquisar.setOnClickListener {
            val intent = Intent(this, ActivityBuscaLivros::class.java)
            startActivity(intent)
        }

        emprestimos.setOnClickListener {
            val intent = Intent(this, ActivityMeusEmprestimos::class.java)
            startActivity(intent)
        }

//        reservas.setOnClickListener {
//            val intent = Intent(this, ActivityGerenciarReservasLivro::class.java)
//            startActivity(intent)
//        }
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