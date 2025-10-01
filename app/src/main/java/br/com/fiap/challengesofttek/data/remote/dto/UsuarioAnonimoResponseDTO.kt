package br.com.fiap.challengesofttek.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsuarioAnonimoResponseDTO(
    val anonymousUserId: String,
    val token: String
)
