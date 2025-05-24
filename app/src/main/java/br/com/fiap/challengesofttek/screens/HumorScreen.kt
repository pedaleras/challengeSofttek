package br.com.fiap.challengesofttek.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengesofttek.dto.HumorRequestDTO
import br.com.fiap.challengesofttek.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.repository.HumorRepository
import br.com.fiap.challengesofttek.service.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Model de entrada de humor
data class HumorEntry(
    val timestamp: String,
    val humorValue: Int,
    val comment: String?
)

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

// Converte nível numérico de humor em descrição verbal
fun getHumorDescription(value: Int): String = when (value) {
    1 -> "Muito Mau"
    2 -> "Mau"
    3 -> "Normal"
    4 -> "Bem"
    5 -> "Muito bem"
    else -> ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumorScreen(
    navController: NavController,
    viewModel: HumorViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val repository = HumorRepository(RetrofitClient.api)
            return HumorViewModel(repository) as T
        }
    })
) {
    var humorValue by remember { mutableStateOf(3) }
    val resposta by viewModel.resposta.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diário de Humor", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF329F6B))
            )
        },
        containerColor = Color(0xFFF1F8E9)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Como você se sente hoje?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))

            Slider(
                value = humorValue.toFloat(),
                onValueChange = { humorValue = it.toInt() },
                valueRange = 1f..5f,
                steps = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Nível de Humor: $humorValue (${getHumorDescription(humorValue)})",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.enviarHumor(humorValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF329F6B))
            ) {
                Text("Salvar")
            }

            Spacer(modifier = Modifier.height(24.dp))
            resposta?.let { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Data: ${entry.dataRegistro}", fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Humor: ${entry.nivel} (${getHumorDescription(entry.nivel)})")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Descrição: ${entry.descricao}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HumorScreenPreview() {
    HumorScreen(navController = rememberNavController())
}
