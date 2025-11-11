package com.unifor.bibliotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.R
import com.unifor.bibliotech.datas.Reserva

class ReservaAdapter(private val listaReservas: List<Reserva>): RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {
    class ReservaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clReserva: ConstraintLayout = itemView.findViewById(R.id.clReserva)
        val tvLivroReservadoTitulo: TextView = itemView.findViewById(R.id.tvLivroReservadoTitulo)
        val tvNomeAlunoReserva: TextView = itemView.findViewById(R.id.tvNomeAlunoReserva)
        val tvDataSolicitacao: TextView = itemView.findViewById(R.id.tvDataSolicitacao)
        val btnAprovar: AppCompatButton = itemView.findViewById(R.id.btnAprovar)
        val btnRecusar: AppCompatButton = itemView.findViewById(R.id.btnRecusar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_reserva_pendente, parent, false)
        return ReservaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = listaReservas[position]
        val context = holder.itemView.context

        holder.tvNomeAlunoReserva.text = reserva.nomeAluno
        holder.tvLivroReservadoTitulo.text = reserva.titulo
        holder.tvDataSolicitacao.text = reserva.dataSolic
    }

    override fun getItemCount() = listaReservas.size
}