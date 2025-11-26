package com.unifor.bibliotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.unifor.bibliotech.R
import com.unifor.bibliotech.datas.Usuario

class UsuarioAdapter(private val listaUsuarios: List<Usuario>, private val onClickDetails: (Usuario) -> Unit, private val onClickRemove: (Usuario) -> Unit): RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {
    private var usuariosExibidos: MutableList<Usuario> = listaUsuarios.toMutableList()
    class UsuarioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clUsuario: ConstraintLayout = itemView.findViewById(R.id.clUsuario)
        val tvUsuarioNome: TextView = itemView.findViewById(R.id.tvUsuarioNome)
        val tvUsuarioDetalhe: TextView = itemView.findViewById(R.id.tvUsuarioDetalhes)
        val viewSeparator: View = itemView.findViewById(R.id.viewSeparator)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val btnVerHistorico: AppCompatButton = itemView.findViewById(R.id.btnVerHistorico)
        val btnExcluir: AppCompatButton = itemView.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_usuario_admin, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuariosExibidos[position]
        val context = holder.itemView.context

        holder.tvUsuarioNome.text = usuario.nome
        holder.tvUsuarioDetalhe.text = usuario.detalhes

        holder.btnVerHistorico.setOnClickListener {
            onClickDetails(usuario)
        }

        holder.btnExcluir.setOnClickListener {
            onClickRemove(usuario)
        }
    }

    override fun getItemCount() = usuariosExibidos.size

    fun filter(query: String) {
        val termoBusca = query.lowercase().trim()

        usuariosExibidos.clear()

        if (termoBusca.isEmpty()) {
            usuariosExibidos.addAll(listaUsuarios)
        } else {
            val resultado = listaUsuarios.filter { usuario ->
                usuario.nome.lowercase().contains(termoBusca) ||
                usuario.detalhes.contains(termoBusca)
            }
            usuariosExibidos.addAll(resultado)
        }
        notifyDataSetChanged()
    }
}