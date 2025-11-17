package com.unifor.bibliotech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

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
        holder.tvDataNotificacao.text = formatarDataNotificacao(notificacao.timestamp)
    }

    private fun formatarDataNotificacao(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val dataNotificacao = timestamp.toDate()
        val agora = java.util.Date()

        val diff = agora.time - dataNotificacao.time
        val diffMinutos = TimeUnit.MILLISECONDS.toMinutes(diff)

        return when {
            diffMinutos < 1 -> "agora"
            diffMinutos < 60 -> "$diffMinutos mins atrás"
            diffMinutos < 1440 -> "${TimeUnit.MINUTES.toHours(diffMinutos)}h atrás"
            else -> {
                val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                sdf.format(dataNotificacao)
            }
        }
    }

    override fun getItemCount() = listaNotificacao.size
}