package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityPerfilAdmin : AppCompatActivity() {
    lateinit var fb: FirebaseFirestore
    private lateinit var adminID: String
    private lateinit var tvNomeAdmin: TextView
    private lateinit var tvEmailAdmin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_admin)
        fb = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaPerfilAdmin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVoltar: ImageButton = findViewById(R.id.btnVoltar)
        val tvEditarDadosAdmin: TextView = findViewById(R.id.tvEditarDadosAdmin)
        tvNomeAdmin = findViewById(R.id.tvNomeAdmin)
        tvEmailAdmin = findViewById(R.id.tvEmailAdmin)
        val btnLogout: AppCompatButton = findViewById(R.id.btnLogout)
        adminID = intent.getStringExtra("ADMIN_ID") ?: ""

        btnVoltar.setOnClickListener {
            finish()
        }

        tvEditarDadosAdmin.setOnClickListener {
            val intent = Intent(this, ActivityEditarPerfilAdmin::class.java)
            intent.putExtra("ADMIN_ID", adminID)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        carregarDadosAdmin()
    }

    private fun carregarDadosAdmin() {
        if (adminID.isEmpty()) return

        fb.collection("usuario")
            .whereEqualTo("id", adminID)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshots != null && !snapshots.isEmpty) {
                    val documento = snapshots.documents[0]

                    val admin = documento.getString("nome") ?: "Administrador"
                    val email = documento.getString("email") ?: "admin@gmail.com"

                    tvNomeAdmin.text = admin
                    tvEmailAdmin.text = email
                } else {
                    Log.d("PERFIL", "Nenhum documento encontrado.")
                }
            }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        if(::adminID.isInitialized && adminID.isNotEmpty()) {
            carregarDadosAdmin()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }
}