package br.com.fiap.challengesofttek.data.repository

import br.com.fiap.challengesofttek.data.remote.dto.AvaliacaoRiscosRequestDTO
import br.com.fiap.challengesofttek.data.remote.dto.AvaliacaoRiscosResponseDTO
import br.com.fiap.challengesofttek.data.remote.service.RetrofitClient

class AvaliacaoRiscosRepository {
    private val api = RetrofitClient.api

    suspend fun enviar(mediaPercentual: Double): AvaliacaoRiscosResponseDTO {
        val request = AvaliacaoRiscosRequestDTO(mediaPercentual)
        return api.enviarAvaliacao(request)
    }
}
