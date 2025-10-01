package br.com.fiap.challengesofttek.ui.screens.avaliacaoriscos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengesofttek.data.remote.dto.AvaliacaoRiscosResponseDTO
import br.com.fiap.challengesofttek.domain.model.PerguntaAvaliacao
import br.com.fiap.challengesofttek.data.repository.AvaliacaoRiscosRepository
import br.com.fiap.challengesofttek.data.repository.QuestionRepository
import br.com.fiap.challengesofttek.data.repository.QuestionRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AvaliacaoViewModel(
    questionRepository: QuestionRepository = QuestionRepositoryImpl
) : ViewModel() {
    private val repository = AvaliacaoRiscosRepository()

    private val _resposta = MutableStateFlow<AvaliacaoRiscosResponseDTO?>(null)
    val resposta: StateFlow<AvaliacaoRiscosResponseDTO?> = _resposta

    fun enviarAvaliacao(mediaPercentual: Double) {
        viewModelScope.launch {
            try {
                val respostaApi = repository.enviar(mediaPercentual)
                _resposta.value = respostaApi
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val perguntas: List<PerguntaAvaliacao> = questionRepository.getPerguntasAvaliacao()
}