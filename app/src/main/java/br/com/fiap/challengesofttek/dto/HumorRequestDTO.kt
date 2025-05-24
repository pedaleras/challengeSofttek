package br.com.fiap.challengesofttek.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HumorRequestDTO(
    val nivel: Int
)
