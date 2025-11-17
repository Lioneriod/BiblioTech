package com.unifor.bibliotech

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Notificacao(
    @DocumentId
    val id: String = "",
    val usuarioId: String = "",
    val titulo: String = "",
    val corpo: String = "",
    val timestamp: Timestamp? = null,
    val lida: Boolean = false
)