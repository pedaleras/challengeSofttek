package br.com.fiap.challengesofttek.data.repository


import br.com.fiap.challengesofttek.data.remote.dto.HumorRequestDTO
import br.com.fiap.challengesofttek.data.remote.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.data.remote.service.ApiService

class HumorRepository(private val api: ApiService) {
    suspend fun enviarHumor(humor: HumorRequestDTO): HumorResponseDTO {
        return api.enviarHumor(humor)
    }
}
