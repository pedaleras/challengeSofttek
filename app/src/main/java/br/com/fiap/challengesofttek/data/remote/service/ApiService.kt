package br.com.fiap.challengesofttek.data.remote.service

import br.com.fiap.challengesofttek.data.remote.dto.AvaliacaoRiscosRequestDTO
import br.com.fiap.challengesofttek.data.remote.dto.AvaliacaoRiscosResponseDTO
import br.com.fiap.challengesofttek.data.remote.dto.HumorRequestDTO
import br.com.fiap.challengesofttek.data.remote.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.data.remote.dto.UsuarioAnonimoResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("avaliacoes-riscos")
    suspend fun enviarAvaliacao(@Body dto: AvaliacaoRiscosRequestDTO): AvaliacaoRiscosResponseDTO

    @GET("humores")
    suspend fun obterListaHumor(): List<HumorResponseDTO>

    @GET("humores/{id}")
    suspend fun obterHumoresPorUsuario(@Path("id") userId: String): List<HumorResponseDTO>

    @POST("humores")
    suspend fun enviarHumor(@Body humor: HumorRequestDTO): HumorResponseDTO

    @POST("api/auth/anonymous")
    suspend fun obterUsuarioAnonimo(): UsuarioAnonimoResponseDTO

}
