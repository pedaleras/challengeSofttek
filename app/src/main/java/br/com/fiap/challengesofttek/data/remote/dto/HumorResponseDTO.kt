package br.com.fiap.challengesofttek.data.remote.dto

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class HumorResponseDTO(
    val id: Long,
    val nivel: Int,
    val descricao: String,
    val dataRegistro: String
)
