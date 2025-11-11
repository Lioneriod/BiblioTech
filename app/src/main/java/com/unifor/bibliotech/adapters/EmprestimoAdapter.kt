package com.unifor.bibliotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.datas.Emprestimo
import com.unifor.bibliotech.R

class EmprestimoAdapter(private val listaEmprestimo: List<Emprestimo>): RecyclerView.Adapter<EmprestimoAdapter.EmprestimoViewHolder>() {
    class EmprestimoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clEmprestimo: ConstraintLayout = itemView.findViewById(R.id.clEmprestimo)
        val tvEmprestimoTitulo: TextView = itemView.findViewById(R.id.tvEmprestimoTitulo)
        val tvEmprestimoAutor: TextView = itemView.findViewById(R.id.tvEmprestimoAutor)
        val tvPrazoDevolucao: TextView = itemView.findViewById(R.id.tvPrazoDevolucao)
        val viewSeparator: View = itemView.findViewById(R.id.viewSeparator)
        val btnRenovar: AppCompatButton = itemView.findViewById(R.id.btnRenovar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmprestimoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_emprestimo, parent, false)
        return EmprestimoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmprestimoViewHolder, position: Int) {
        val emprestimo = listaEmprestimo[position]
        val context = holder.itemView.context

        holder.tvEmprestimoTitulo.text = emprestimo.titulo
        holder.tvEmprestimoAutor.text = emprestimo.autor
        holder.tvPrazoDevolucao.text = emprestimo.prazo
    }

    override fun getItemCount() = listaEmprestimo.size
}