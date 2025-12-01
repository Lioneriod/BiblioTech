package com.unifor.bibliotech

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ActivityEditarPerfilAdmin : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val configuration = Configuration(newBase.resources.configuration)

        configuration.fontScale = 1.0f

        val context = ContextWrapper(newBase.createConfigurationContext(configuration))
        super.attachBaseContext(context)
    }
    lateinit var fb: FirebaseFirestore
    private lateinit var adminID: String
    private lateinit var etNomeAdmin: EditText
    private lateinit var etEmailAdmin: EditText
    private lateinit var etSenhaAdmin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_perfil_admin)
        fb = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaEditarPerfilAdmin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVoltar: ImageButton = findViewById(R.id.btnVoltar)
        val btnSalvar: AppCompatButton = findViewById(R.id.btnSalvar)
        etNomeAdmin = findViewById(R.id.etNomeAdmin)
        etEmailAdmin = findViewById(R.id.etEmailAdmin)
        etSenhaAdmin = findViewById(R.id.etSenhaAdmin)
        adminID = intent.getStringExtra("ADMIN_ID") ?: ""

        btnVoltar.setOnClickListener {
            finish()
        }

        btnSalvar.setOnClickListener {
            val nomeAdmin = etNomeAdmin.text.toString().trim()
            val emailAdmin = etEmailAdmin.text.toString().trim()
            val senhaAdmin = etSenhaAdmin.text.toString()

            if (nomeAdmin.isEmpty() && emailAdmin.isEmpty() && senhaAdmin.isEmpty()) {
                Toast.makeText(this, "Por favor, Preencha ao menos um campo para salvar.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updateMap = hashMapOf<String, Any>()

            if (nomeAdmin.isNotEmpty()) {
                updateMap["nome"] = nomeAdmin
            }

            if (emailAdmin.isNotEmpty()) {
                updateMap["email"] = emailAdmin
            }

            if (senhaAdmin.isNotEmpty()) {
                updateMap["senha"] = senhaAdmin
            }

            btnSalvar.isEnabled = false
            btnSalvar.text = "Salvando..."

            fb.collection("usuario")
                .whereEqualTo("id", adminID)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val documento = querySnapshot.documents[0]

                        documento.reference.update(updateMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Erro ao atualizar: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Administrador não encontrado no banco", Toast.LENGTH_SHORT).show()
                        btnSalvar.isEnabled = true
                        btnSalvar.text = "Salvar"
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro de conexão: ${e.message}", Toast.LENGTH_SHORT).show()
                    btnSalvar.isEnabled = true
                    btnSalvar.text = "Salvar"
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