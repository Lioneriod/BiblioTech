package com.unifor.bibliotech

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat

class ActivityEmissaoRelatorio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emissao_relatorio)
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
            tvResultado.text = "Top 5 Livros Mais Emprestados:\n1. Five Night's At Freddy's: into The Pit\n2. Coraline\n3. O pequeno príncipe\n4. Five Night's At Freddy's: Silver Eyes\n5. Five Night's At Freddy's: The Fourth Closet"
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