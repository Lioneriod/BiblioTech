package com.unifor.bibliotech

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.adapters.LivroAdapter
import com.unifor.bibliotech.datas.Livro

class ActivityBuscaLivros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_busca_livros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaBuscarLivros)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        val dadosLivros = listOf(
            Livro(
                titulo = "Five Night's At Freddy's: Into The Pit",
                autor = "Scott Cawthon",
                anoPub = "01/01/2019",
                status = true
            ),
            Livro(
                titulo = "Five Night's At Freddy's: The Silver Eyes",
                autor = "Scott Cawthon",
                anoPub = "01/01/2017",
                status = true
            ),
            Livro(
                titulo = "Five Night's At Freddy's: The Fourth Closet",
                autor = "Scott Cawthon",
                anoPub = "01/01/2018",
                status = true
            ),
            Livro(
                titulo = "Meu pequeno príncipe",
                autor = "Júlio Cézar",
                anoPub = "01/01/2017",
                status = false
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvResultadosBusca)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val onItemClick: (Livro) -> Unit = { livroClicado ->
            val intent = Intent(this, ActivityDetalhesLivro::class.java)

            intent.putExtra("TITULO_LIVRO", livroClicado.titulo)
            intent.putExtra("AUTOR_LIVRO", livroClicado.autor)
            intent.putExtra("ANO_PUB_LIVRO", livroClicado.anoPub)
            intent.putExtra("STATUS_LIVRO", livroClicado.status)
            startActivity(intent)
        }
        val livroAdapter = LivroAdapter(dadosLivros, onItemClick)
        recyclerView.adapter = livroAdapter

        val etBusca: EditText = findViewById(R.id.etBusca)

        etBusca.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    livroAdapter.filter(it.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

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