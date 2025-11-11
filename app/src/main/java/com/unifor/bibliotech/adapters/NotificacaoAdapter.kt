package com.unifor.bibliotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.datas.Notificacao
import com.unifor.bibliotech.R

class NotificacaoAdapter(private val listaNotificacao: List<Notificacao>): RecyclerView.Adapter<NotificacaoAdapter.NotificacaoViewHolder>() {
    class NotificacaoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clItemNotificacao: ConstraintLayout = itemView.findViewById(R.id.itemNotificacao)
        val ivIconeNotificacao: ImageView = itemView.findViewById(R.id.ivIconeNotificacao)
        val tvTituloNotificacao: TextView = itemView.findViewById(R.id.tvTituloNotificacao)
        val tvCorpoNotificacao: TextView = itemView.findViewById(R.id.tvCorpoNotificacao)
        val tvDataNotificacao: TextView = itemView.findViewById(R.id.tvDataNotificacao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_notificacao, parent, false)
        return NotificacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificacaoViewHolder, position: Int) {
        val notificacao = listaNotificacao[position]
        val context = holder.itemView.context

        holder.tvTituloNotificacao.text = notificacao.titulo
        holder.tvCorpoNotificacao.text = notificacao.corpo
        holder.tvDataNotificacao.text = notificacao.data
    }

    override fun getItemCount() = listaNotificacao.size
}