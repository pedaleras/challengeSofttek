package br.com.fiap.challengesofttek.repository


import br.com.fiap.challengesofttek.dto.HumorRequestDTO
import br.com.fiap.challengesofttek.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.service.ApiService

class HumorRepository(private val api: ApiService) {
    suspend fun enviarHumor(humor: HumorRequestDTO): HumorResponseDTO {
        return api.enviarHumor(humor)
    }
}
