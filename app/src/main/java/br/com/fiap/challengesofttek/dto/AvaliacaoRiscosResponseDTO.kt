package br.com.fiap.challengesofttek.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvaliacaoRiscosResponseDTO(
    val id: Long,
    val dataCriacao: String,
    val mediaPercentual: Double,
    val categoriaFinal: String
)
