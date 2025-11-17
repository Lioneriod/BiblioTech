package com.unifor.bibliotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.datas.MensagemChatBot
import com.unifor.bibliotech.R

class MensagemChatBotAdapter(var message: MutableList<MensagemChatBot>): RecyclerView.Adapter<MensagemChatBotAdapter.MensagemChatBotViewHolder>() {
    class MensagemChatBotViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clListMessage: ConstraintLayout = itemView.findViewById(R.id.list_message)
        val tvBotMessage: TextView = itemView.findViewById(R.id.tvBotMessage)
        val tvUserMessage: TextView = itemView.findViewById(R.id.tvUserMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemChatBotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_chat_message, parent, false)
        return MensagemChatBotViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensagemChatBotViewHolder, position: Int) {
        val mensagem = message[position]

        if (mensagem.isUser) {
            holder.tvUserMessage.visibility = View.VISIBLE
            holder.tvBotMessage.visibility = View.GONE
            holder.tvUserMessage.text = mensagem.mensagem
        } else {
            holder.tvUserMessage.visibility = View.GONE
            holder.tvBotMessage.visibility = View.VISIBLE
            holder.tvBotMessage.text = mensagem.mensagem
        }
    }

    override fun getItemCount() = message.size

    fun addMessage(mensagem: MensagemChatBot) {
        message.add(mensagem)
        notifyItemInserted(message.size -1)
    }
}