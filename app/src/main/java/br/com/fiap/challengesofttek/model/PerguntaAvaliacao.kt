package br.com.fiap.challengesofttek.model

data class PerguntaAvaliacao(
    val id: Int,
    val texto: String,
    val opcoes: List<OpcaoResposta>
)