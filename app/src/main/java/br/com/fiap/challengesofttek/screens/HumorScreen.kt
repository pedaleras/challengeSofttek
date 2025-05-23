package br.com.fiap.challengesofttek.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Model de entrada de humor
data class HumorEntry(
    val timestamp: String,
    val humorValue: Int,
    val comment: String?
)

// ViewModel para gerenciar entradas de humor
class HumorViewModel : ViewModel() {
    private val _entries = mutableStateListOf<HumorEntry>()
    val entries: List<HumorEntry> get() = _entries

    fun addEntry(value: Int, comment: String?) {
        val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        _entries.add(0, HumorEntry(timestamp = now, humorValue = value, comment = comment))
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
    viewModel: HumorViewModel = viewModel()
) {
    var humorValue by remember { mutableStateOf(3) }
    var comment by remember { mutableStateOf("") }
    val entries = viewModel.entries

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diário de Humor", color = Color.White) },
                navigationIcon = {IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Como você se sente hoje?",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
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
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentários (opcional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.addEntry(humorValue, comment.ifBlank { null })
                    comment = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF329F6B))
            ) {
                Text("Salvar")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Histórico de Humor",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            entries.forEach { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = entry.timestamp, fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Humor: ${entry.humorValue} (${getHumorDescription(entry.humorValue)})")
                        entry.comment?.let {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = it, style = MaterialTheme.typography.bodySmall)
                        }
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
