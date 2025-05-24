package br.com.fiap.challengesofttek.repository

import br.com.fiap.challengesofttek.dto.AvaliacaoRiscosRequestDTO
import br.com.fiap.challengesofttek.dto.AvaliacaoRiscosResponseDTO
import br.com.fiap.challengesofttek.service.RetrofitClient

class AvaliacaoRiscosRepository {
    private val api = RetrofitClient.api

    suspend fun enviar(mediaPercentual: Double): AvaliacaoRiscosResponseDTO {
        val request = AvaliacaoRiscosRequestDTO(mediaPercentual)
        return api.enviarAvaliacao(request)
    }
}
