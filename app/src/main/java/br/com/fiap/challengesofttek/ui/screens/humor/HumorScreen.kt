package br.com.fiap.challengesofttek.ui.screens.humor

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengesofttek.data.repository.HumorRepository
import br.com.fiap.challengesofttek.data.remote.service.RetrofitClient

// Model de entrada de humor
data class HumorEntry(
    val timestamp: String,
    val humorValue: Int,
    val comment: String?
)

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
