package br.com.fiap.challengesofttek.data.repository

import br.com.fiap.challengesofttek.data.remote.dto.UsuarioAnonimoResponseDTO
import br.com.fiap.challengesofttek.data.remote.service.ApiService

class LoginRepository(private val api: ApiService) {
    suspend fun obterToken(): UsuarioAnonimoResponseDTO {
        return api.obterUsuarioAnonimo()
    }
}