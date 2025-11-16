package com.unifor.bibliotech

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Emprestimo(
    @DocumentId
    val id: String = "",
    val livroId: String = "",
    val usuarioId: String = "",
    val titulo: String = "",
    val autor: String = "",
    val prazo: Timestamp? = null,
    val dataEmprestimo: Timestamp? = null
)