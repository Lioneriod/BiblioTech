package com.unifor.bibliotech

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityChatBot : AppCompatActivity() {
    private lateinit var mensagemChatBotAdapter: MensagemChatBotAdapter
    private lateinit var rvChat: RecyclerView
    private lateinit var etMessageInput: EditText
    private lateinit var btnSendMessage: ImageButton

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_bot)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_main_root_chatbot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVoltar: ImageButton = findViewById(R.id.btnVoltar)
        val messages = mutableListOf<MensagemChatBot>()
        mensagemChatBotAdapter = MensagemChatBotAdapter(messages)
        rvChat = findViewById(R.id.rvChatMessages)
        etMessageInput = findViewById(R.id.etMessageInput)
        btnSendMessage = findViewById(R.id.btnSendMessage)

        rvChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        rvChat.adapter = mensagemChatBotAdapter

        mensagemChatBotAdapter.addMessage(MensagemChatBot("Olá! Sou o assistente virtual da Bibliotech. Posso te ajudar com empréstimos, reservas e dúvidas gerais. Digite \"ajuda\" para ver os comandos.", false))

        btnVoltar.setOnClickListener {
            finish()
        }

        btnSendMessage.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val text = etMessageInput.text.toString().trim()
        if (text.isNotEmpty()) {
            mensagemChatBotAdapter.addMessage(MensagemChatBot(text, true))
            rvChat.scrollToPosition(mensagemChatBotAdapter.itemCount -1)

            etMessageInput.text.clear()

            handler.postDelayed({
                val botResponse = getBotResponse(text)
                mensagemChatBotAdapter.addMessage(MensagemChatBot(botResponse, false))
                rvChat.scrollToPosition(mensagemChatBotAdapter.itemCount -1)
            }, 800)
        }
    }

    private fun getBotResponse(userInput: String): String {
        val input = userInput.lowercase()

        return when {
            input.contains("horario") -> "A biblioteca funciona segunda a sexta, das 8h às 21h. Sábados, das 9h às 14h."
            input.contains("reserva") -> "Para reservar, busque o livro e clique em 'Reservar Livro' na tela de detalhes. Você será notificado quando estiver disponível."
            input.contains("renovar") -> "Você pode renovar seu empréstimo pela tela 'Meus Empréstimos', se o livro não estiver reservado por outro aluno."
            input.contains("cadastro") -> "Para criar uma conta, clique em 'Cadastre-se' na tela de login."

            input == "ajuda" || input.contains("comandos") -> "Comandos: Horário | Reserva | Renovar | Cadastro"
            input.contains("obrigado") || input.contains("valeu") -> "De nada! Estou aqui para ajudar."

            else -> "Desculpe, não entendi. Por favor, reformule sua pergunta ou digite \"ajuda\" para ver as opções."
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