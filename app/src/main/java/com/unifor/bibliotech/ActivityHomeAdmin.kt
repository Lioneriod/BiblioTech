package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class ActivityHomeAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaHomeAdmin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logout: TextView = findViewById(R.id.tvLogoutAdmin)
        val gerenciarUsuarios: ConstraintLayout = findViewById(R.id.cardUsuarios)
        val relatorio: ConstraintLayout = findViewById(R.id.cardRelatorio)
        val reservas: ConstraintLayout = findViewById(R.id.telaGerenciarReservasLivro)

        logout.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        gerenciarUsuarios.setOnClickListener {
            val intent = Intent(this, ActivityListagemUsuarios::class.java)
            startActivity(intent)
        }

        relatorio.setOnClickListener {
            val intent = Intent(this, ActivityEmissaoRelatorio::class.java)
            startActivity(intent)
        }

        reservas.setOnClickListener {
            val intent = Intent(this, ActivityGerenciarReservasLivro::class.java)
            startActivity(intent)
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