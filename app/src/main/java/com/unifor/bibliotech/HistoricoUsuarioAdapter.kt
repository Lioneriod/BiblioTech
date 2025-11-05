package com.unifor.bibliotech

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class HistoricoUsuarioAdapter(private val listaHistorico: List<HistoricoUsuario>): RecyclerView.Adapter<HistoricoUsuarioAdapter.HistoricoUsuarioViewHolder>() {
    class HistoricoUsuarioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clHistorico: ConstraintLayout = itemView.findViewById(R.id.clHistorico)
        val tvAcaoTipo: TextView = itemView.findViewById(R.id.tvAcaoTipo)
        val tvDataAcao: TextView = itemView.findViewById(R.id.tvDataAcao)
        val tvLivroTitulo: TextView = itemView.findViewById(R.id.tvLivroTitulo)
        val tvStatusDetalhe: TextView = itemView.findViewById(R.id.tvStatusDetalhe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoUsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_historico_admin, parent, false)
        return HistoricoUsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoUsuarioViewHolder, position: Int) {
        val historico = listaHistorico[position]
        val context = holder.itemView.context

        holder.tvAcaoTipo.text = historico.tipoAcao
        holder.tvDataAcao.text = historico.data
        holder.tvLivroTitulo.text = historico.tituloLivro
        holder.tvStatusDetalhe.text = historico.statusDetalhe
    }

    override fun getItemCount() = listaHistorico.size
}