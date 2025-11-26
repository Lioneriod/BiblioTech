package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.jvm.java

class ActivityHomeAdmin : AppCompatActivity() {
    private lateinit var adminID: String
    lateinit var fb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_admin)
        fb = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaHomeAdmin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gerenciarUsuarios: ConstraintLayout = findViewById(R.id.cardUsuarios)
        val relatorio: ConstraintLayout = findViewById(R.id.cardRelatorio)
        val perfil: ImageView = findViewById(R.id.ivAdminProfile)
        adminID = intent.getStringExtra("ADMIN_ID") ?: ""
        val tvSaudacao: TextView = findViewById(R.id.tvSaudacaoAdmin)

        gerenciarUsuarios.setOnClickListener {
            val intent = Intent(this, ActivityListagemUsuarios::class.java)
            startActivity(intent)
        }

        relatorio.setOnClickListener {
            val intent = Intent(this, ActivityEmissaoRelatorio::class.java)
            startActivity(intent)
        }

        perfil.setOnClickListener {
            val intent = Intent(this, ActivityPerfilAdmin::class.java)
            intent.putExtra("ADMIN_ID", adminID)
            startActivity(intent)
        }

        carregarDadosDoAdmin(tvSaudacao)
    }

    private fun carregarDadosDoAdmin(nomeAdmin: TextView) {
        fb.collection("usuario")
            .whereEqualTo("id", adminID)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshots != null && !snapshots.isEmpty) {
                    val documento = snapshots.documents[0]

                    val nome = documento.getString("nome") ?: "Administrador"

                    nomeAdmin.text = nome
                }
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