package br.com.fiap.challengesofttek.ui.screens.humor

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengesofttek.data.remote.dto.HumorRequestDTO
import br.com.fiap.challengesofttek.data.remote.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.data.repository.HumorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// ViewModel para gerenciar entradas de humor
class HumorViewModel(
    private val repository: HumorRepository
) : ViewModel() {
    private val _entries = mutableStateListOf<HumorEntry>()
    val entries: List<HumorEntry> get() = _entries

    fun addEntry(value: Int, comment: String?) {
        val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        _entries.add(0, HumorEntry(timestamp = now, humorValue = value, comment = comment))
    }

    private val _resposta = MutableStateFlow<HumorResponseDTO?>(null)
    val resposta: StateFlow<HumorResponseDTO?> = _resposta

    fun enviarHumor(nivel: Int) {
        viewModelScope.launch {
            try {
                Log.d("HUMOR", "Enviando humor com nível: $nivel")
                val respostaApi = repository.enviarHumor(HumorRequestDTO(nivel))
                _resposta.value = respostaApi
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