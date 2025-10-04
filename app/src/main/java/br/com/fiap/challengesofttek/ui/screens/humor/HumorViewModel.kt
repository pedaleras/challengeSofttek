package br.com.fiap.challengesofttek.ui.screens.humor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengesofttek.data.local.UserPreferences
import br.com.fiap.challengesofttek.data.remote.dto.HumorRequestDTO
import br.com.fiap.challengesofttek.data.remote.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.data.repository.HumorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HumorViewModel(
    private val repository: HumorRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _humores = MutableStateFlow<List<HumorResponseDTO>>(emptyList())
    val humores: StateFlow<List<HumorResponseDTO>> = _humores

    private val _resposta = MutableStateFlow<HumorResponseDTO?>(null)
    val resposta: StateFlow<HumorResponseDTO?> = _resposta

    fun carregarHumores() {
        viewModelScope.launch {
            // Pega userId do DataStore
            val userId = userPreferences.getUserData()
                .map { it.second } // assumindo que segundo = userId
                .firstOrNull()

            if (userId.isNullOrEmpty()) {
                Log.e("HUMOR", "UserId não encontrado no DataStore")
                _humores.value = emptyList()
                return@launch
            }

            try {
                val lista = repository.obterHumoresPorUsuario(userId)
                _humores.value = lista
            } catch (e: Exception) {
                Log.e("HUMOR", "Erro ao carregar humores", e)
                _humores.value = emptyList()
            }
        }
    }

    fun enviarHumor(nivel: Int) {
        viewModelScope.launch {
            try {
                val respostaApi = repository.enviarHumor(HumorRequestDTO(nivel))
                _resposta.value = respostaApi
                // Recarrega lista após enviar
                carregarHumores()
            } catch (e: Exception) {
                Log.e("HUMOR", "Erro ao enviar humor", e)
                _resposta.value = null
            }
        }
    }

    fun limparResposta() {
        _resposta.value = null
    }
}
