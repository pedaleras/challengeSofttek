package br.com.fiap.challengesofttek.domain.model

data class PerguntaAvaliacao(
    val id: Int,
    val texto: String,
    val opcoes: List<OpcaoResposta>
)