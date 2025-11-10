package com.unifor.bibliotech

import com.google.firebase.firestore.DocumentId

data class Livro(
    @DocumentId
    val id: String = "",
    val status: Boolean = false,
    val titulo: String = "",
    val autor: String = "",
    val anoPub: String = "",
    val sinopse: String = ""
)