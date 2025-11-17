package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityPerfilAdmin : AppCompatActivity() {

    private lateinit var adminID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaPerfilAdmin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVoltar: ImageButton = findViewById(R.id.btnVoltar)
        val tvEditarDadosAdmin: TextView = findViewById(R.id.tvEditarDadosAdmin)
        val tvNomeAdmin: TextView = findViewById(R.id.tvNomeAdmin)
        val nomeDoAdmin = intent.getStringExtra("NOME_ADMIN")
        adminID = intent.getStringExtra("ADMIN_ID") ?: ""

        if (nomeDoAdmin != null && nomeDoAdmin.isNotEmpty()) {
            tvNomeAdmin.text = nomeDoAdmin
        } else {
            tvNomeAdmin.text = "Administrador"
        }

        btnVoltar.setOnClickListener {
            finish()
        }

        tvEditarDadosAdmin.setOnClickListener {
            val intent = Intent(this, ActivityEditarPerfilAdmin::class.java)
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