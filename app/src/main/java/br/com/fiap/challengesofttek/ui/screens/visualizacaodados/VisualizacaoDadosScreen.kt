package br.com.fiap.challengesofttek.ui.screens.visualizacaodados

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizacaoDadosScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Evolução Emocional", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
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
                .padding(16.dp)
        ) {
            Text(
                text = "Acompanhamento dos níveis de humor ao longo do tempo",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // Dados fictícios de humor (1 a 5) ao longo de 7 dias
            val humorEntryModel = entryModelOf(
                1f to 2f,
                2f to 3f,
                3f to 4f,
                4f to 3f,
                5f to 5f,
                6f to 4f,
                7f to 5f
            )

            val humorEntries: List<ChartEntry> = humorEntryModel.entries[0]

            Chart(
                chart = lineChart(),
                model = entryModelOf(1f to 2f, 2f to 3f, 3f to 4f, 4f to 3f, 5f to 5f, 6f to 4f, 7f to 5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Legenda:\n1 - Muito mal\n2 - Mal\n3 - Neutro\n4 - Bem\n5 - Muito bem",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}
