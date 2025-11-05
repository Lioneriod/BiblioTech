package com.unifor.bibliotech

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class LivroAdapter(private val listaLivros: List<Livro>, private val onItemClicked: (Livro) -> Unit): RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {
    private var livrosExibidos: MutableList<Livro> = listaLivros.toMutableList()

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
        val livro = livrosExibidos[position]
        val context = holder.itemView.context

        holder.tvAutor.text = livro.autor
        holder.tvTitulo.text = livro.titulo
        holder.tvAnoPub.text = livro.anoPub
        holder.tvStatus.text = if (livro.status) "Disponível" else "Indisponível"

        holder.itemView.setOnClickListener {
            onItemClicked(livro)
        }
    }

    override fun getItemCount() = livrosExibidos.size

    fun filter(query: String) {
        val termoBusca = query.lowercase().trim()

        livrosExibidos.clear()

        if(termoBusca.isEmpty()) {
            livrosExibidos.addAll(listaLivros)
        } else {
            val resultados = listaLivros.filter { livro ->
                livro.titulo.lowercase().contains(termoBusca) ||
                livro.autor.lowercase().contains(termoBusca)
            }
            livrosExibidos.addAll(resultados)
        }
        notifyDataSetChanged()
    }
}