package br.com.fiap.challengesofttek.repository

import br.com.fiap.challengesofttek.model.OpcaoResposta
import br.com.fiap.challengesofttek.model.PerguntaAvaliacao

// domain/data/QuestionRepository.kt
interface QuestionRepository {
    fun getPerguntasAvaliacao(): List<PerguntaAvaliacao>
}

// domain/data/QuestionRepositoryImpl.kt
object QuestionRepositoryImpl : QuestionRepository {
    override fun getPerguntasAvaliacao() = listOf(
        PerguntaAvaliacao(
            id = 1,
            texto = "Nas últimas duas semanas, com que frequência você sentiu ansiedade intensa que afetou seu bem‑estar geral?",
            opcoes = listOf(
                OpcaoResposta("Neutro", 13), // Média de 0-25
                OpcaoResposta("Leve", 38),   // Média de 26-50
                OpcaoResposta("Moderado", 63),// Média de 51-75
                OpcaoResposta("Agudo", 88)   // Média de 76-100
            )
        ),
        PerguntaAvaliacao(
            id = 2,
            texto = "Você teve episódios de tristeza profunda que interferiram em sua motivação diária?",
            opcoes = listOf(
                OpcaoResposta("Neutro", 13),
                OpcaoResposta("Leve", 38),
                OpcaoResposta("Moderado", 63),
                OpcaoResposta("Agudo", 88)
            )
        ),
        PerguntaAvaliacao(
            id = 3,
            texto = "Sentiu‑se exausto(a) mesmo após períodos curtos de descanso?",
            opcoes = listOf(
                OpcaoResposta("Neutro", 13),
                OpcaoResposta("Leve", 38),
                OpcaoResposta("Moderado", 63),
                OpcaoResposta("Agudo", 88)
            )
        ),
        PerguntaAvaliacao(
            id = 4,
            texto = "Percebeu falta de interesse em atividades que antes lhe davam prazer?",
            opcoes = listOf(
                OpcaoResposta("Neutro", 13),
                OpcaoResposta("Leve", 38),
                OpcaoResposta("Moderado", 63),
                OpcaoResposta("Agudo", 88)
            )
        ),
        PerguntaAvaliacao(
            id = 5,
            texto = "Notou irritabilidade ou reações emocionais desproporcionais ao seu entorno?",
            opcoes = listOf(
                OpcaoResposta("Neutro", 13),
                OpcaoResposta("Leve", 38),
                OpcaoResposta("Moderado", 63),
                OpcaoResposta("Agudo", 88)
            )
        )
    )

}
