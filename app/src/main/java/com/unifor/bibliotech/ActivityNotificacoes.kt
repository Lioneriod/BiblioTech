package com.unifor.bibliotech

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.adapters.NotificacaoAdapter
import com.unifor.bibliotech.datas.Notificacao

class ActivityNotificacoes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notificacoes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaNotificacoes)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar: ImageButton = findViewById(R.id.btnVoltar)

        val dadosNotificacoes = listOf(
            Notificacao(
                titulo = "Livro \"Five Night's At Freddy's: Silver Eyes\" disponível!",
                corpo = "Sua reserva foi atendida! Você tem até 40h para retirar o livro da biblioteca! Corre!",
                data = "10 mins atrás"
            ),
            Notificacao(
                titulo = "Seu livro \"Coraline\" está perto de expirar. Atenção à devolução!",
                corpo = "Faltam 5 dias para o seu livro expirar. Você tem até esse dia para se dirigir á biblioteca para devolve-lo",
                data = "1 dia atrás"
            ),
            Notificacao(
                titulo = "Livro \"Harry Potter\" recém adicionado na biblioteca! Corre pra pegar!",
                corpo = "Um novo livro acaba de ser adicionado na biblioteca. Garanta sua reserva ou empréstimo já!",
                data = "10/05"
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvNotificacoes)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = NotificacaoAdapter(dadosNotificacoes)

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