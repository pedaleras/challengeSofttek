package br.com.fiap.challengesofttek.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvaliacaoRiscosRequestDTO(
    val mediaPercentual: Double
)

