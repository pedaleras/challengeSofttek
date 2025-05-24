package br.com.fiap.challengesofttek.service

import br.com.fiap.challengesofttek.dto.AvaliacaoRiscosRequestDTO
import br.com.fiap.challengesofttek.dto.AvaliacaoRiscosResponseDTO
import br.com.fiap.challengesofttek.dto.HumorRequestDTO
import br.com.fiap.challengesofttek.dto.HumorResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("avaliacoes-riscos")
    suspend fun enviarAvaliacao(@Body dto: AvaliacaoRiscosRequestDTO): AvaliacaoRiscosResponseDTO

    @POST("humores")
    suspend fun enviarHumor(@Body humor: HumorRequestDTO): HumorResponseDTO

}
