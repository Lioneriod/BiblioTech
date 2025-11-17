package com.unifor.bibliotech

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.type.*
import com.unifor.bibliotech.adapters.MensagemChatBotAdapter
import com.unifor.bibliotech.datas.MensagemChatBot

class ActivityChatBot : AppCompatActivity() {
    private lateinit var mensagemChatBotAdapter: MensagemChatBotAdapter
    private lateinit var rvChat: RecyclerView
    private lateinit var etMessageInput: EditText
    private lateinit var btnSendMessage: ImageButton
    private lateinit var generativeModel: GenerativeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_bot)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_main_root_chatbot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apiKey = com.unifor.bibliotech.BuildConfig.GEMINI_API_KEY


        generativeModel = GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = apiKey,
            systemInstruction = content {
                text("Você é o assistente virtual da Bibliotech (Um aplicativo da biblioteca da UNIFOR)\n" +
                        "        Seu objetivo é ajudar os usuários com base nas funcionalidades do aplicativo:\n" +
                        "        - Horário de Funcionamento: Segunda a Sexta, das 8h às 21h. Sábados, das 9h às 13h.\n" +
                        "        - Regras de Empréstimo: O prazo padrão é de 15 dias.\n" +
                        "        - O aplicativo permite Buscar Livros, Ver Meus Empréstimos e Minhas Reservas (tela principal).\n" +
                        "        - O Painel Administrativo tem gerenciamento de Reservas, Usuários e Relatórios.\n" +
                        "        Responda sempre em português e de forma concisa.")
            }
        )
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

            val typingMessage = MensagemChatBot("...", false)
            mensagemChatBotAdapter.addMessage(typingMessage)
            rvChat.scrollToPosition(mensagemChatBotAdapter.itemCount -1)

            lifecycleScope.launch {
                try {
                    val response = generativeModel.generateContent(text)

                    mensagemChatBotAdapter.message.remove(typingMessage)
                    mensagemChatBotAdapter.notifyItemRemoved(mensagemChatBotAdapter.message.size)

                    val botResponse = response.text?: "Desculpe, não consegui gerar uma resposta."
                    mensagemChatBotAdapter.addMessage(MensagemChatBot(botResponse, false))
                    rvChat.scrollToPosition(mensagemChatBotAdapter.itemCount -1)
                } catch (e: Exception) {
                    mensagemChatBotAdapter.message.remove(typingMessage)
                    mensagemChatBotAdapter.notifyItemRemoved(mensagemChatBotAdapter.message.size)
                    mensagemChatBotAdapter.addMessage(
                        MensagemChatBot(
                            "Erro de conexão: ${e.message}",
                            false
                        )
                    )
                }
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