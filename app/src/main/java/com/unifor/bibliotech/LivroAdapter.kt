package com.unifor.bibliotech

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class LivroAdapter(private val listaLivros: List<Livro>): RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {
    class LivroViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clLivro: ConstraintLayout = itemView.findViewById(R.id.cardLivro)
        val tvStatus: TextView = itemView.findViewById(R.id.tvLivroStatus)
        val tvTitulo: TextView = itemView.findViewById(R.id.tvLivroTitulo)
        val tvAutor: TextView = itemView.findViewById(R.id.tvLivroAutor)
        val tvAnoPub: TextView = itemView.findViewById(R.id.tvLivroAno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_livro, parent, false)
        return LivroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = listaLivros[position]
        val context = holder.itemView.context

        holder.tvAutor.text = livro.autor
        holder.tvTitulo.text = livro.titulo
        holder.tvAnoPub.text = livro.anoPub

        if(livro.status) {
            TODO()
        } else {
            TODO()
        }

        holder.itemView.setOnClickListener {
            TODO()
        }
    }

    override fun getItemCount() = listaLivros.size
}