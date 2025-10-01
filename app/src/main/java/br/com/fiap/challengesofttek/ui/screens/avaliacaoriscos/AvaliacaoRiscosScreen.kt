package br.com.fiap.challengesofttek.ui.screens.avaliacaoriscos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengesofttek.components.PerguntaItem
import br.com.fiap.challengesofttek.domain.model.OpcaoResposta
import br.com.fiap.challengesofttek.util.calcularCategoriaFinal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvaliacaoRiscosScreen(
    navController: NavController,
    viewModel: AvaliacaoViewModel = viewModel()
) {
    val perguntas = viewModel.perguntas
    val respostasSelecionadas = remember { mutableStateMapOf<Int, OpcaoResposta>() }
    var resultadoFinal by remember { mutableStateOf<String?>(null) }
    var mediaPercentuaisFinal by remember { mutableStateOf<Double?>(null) }
    var mostrarErro by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Avaliação de Riscos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (resultadoFinal != null) {
                            resultadoFinal = null
                            mediaPercentuaisFinal = null
                            respostasSelecionadas.clear()
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF329F6B))
            )
        },
        containerColor = Color(0xFFE8F5E9)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (resultadoFinal == null) {
                Text(
                    text = "Responda às perguntas abaixo:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(12.dp))
                perguntas.forEach { pergunta ->
                    PerguntaItem(
                        pergunta = pergunta,
                        opcaoSelecionada = respostasSelecionadas[pergunta.id],
                        onSelect = { respostasSelecionadas[pergunta.id] = it; mostrarErro = false }
                    )
                    Spacer(Modifier.height(16.dp))
                }
                if (mostrarErro) {
                    Text(
                        text = "Responda todas as perguntas",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                        if (respostasSelecionadas.size == perguntas.size) {
                            val soma = respostasSelecionadas.values.sumOf { it.percentualMedio }
                            mediaPercentuaisFinal = soma.toDouble() / perguntas.size
                            resultadoFinal = calcularCategoriaFinal(mediaPercentuaisFinal!!)

                            viewModel.enviarAvaliacao(mediaPercentuaisFinal!!)
                        } else mostrarErro = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF329F6B))
                ) {
                    Text("Enviar")
                }
            } else {
                Text(
                    text = "Resultado: $resultadoFinal (${
                        String.format(
                            "%.1f%%",
                            mediaPercentuaisFinal
                        )
                    })",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = when (resultadoFinal) {
                        "Neutro" -> "Sua condição está dentro do esperado."
                        "Leve" -> "Alguns episódios podem afetar sem prejudicar atividades."
                        "Moderado" -> "Sentimentos persistentes que afetam bem-estar."
                        else -> "Sentimento intenso que pode impactar qualidade de vida."
                    },
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        resultadoFinal = null
                        respostasSelecionadas.clear()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF329F6B))
                ) {
                    Text("Refazer Avaliação")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AvaliacaoRiscosPreview() {
    AvaliacaoRiscosScreen(navController = rememberNavController())
}
