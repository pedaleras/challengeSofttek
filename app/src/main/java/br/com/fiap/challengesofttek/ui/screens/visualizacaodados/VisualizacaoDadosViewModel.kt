package br.com.fiap.challengesofttek.ui.screens.visualizacaodados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengesofttek.data.remote.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.data.repository.HumorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VisualizacaoDadosViewModel(private val repository: HumorRepository) : ViewModel() {

    private val _humores = MutableStateFlow<List<HumorResponseDTO>>(emptyList())
    val humores: StateFlow<List<HumorResponseDTO>> = _humores

    init {
        carregarHumores()
    }

    private fun carregarHumores() {
        viewModelScope.launch {
            try {
                val response = repository.obterListaHumor()
                _humores.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
