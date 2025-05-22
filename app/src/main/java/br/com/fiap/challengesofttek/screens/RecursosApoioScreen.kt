package br.com.fiap.challengesofttek.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecursosApoioScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recursos de Apoio", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Aqui estão alguns recursos que podem te ajudar:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            RecursoCard(titulo = "Contatos de Apoio", conteudo = listOf(
                "CVV – 188 (Centro de Valorização da Vida)",
                "Unidades CAPS ou UBS da sua região",
                "Emergência médica: 192"
            ))

            Spacer(modifier = Modifier.height(16.dp))

            RecursoCard(titulo = "Atividades de Relaxamento", conteudo = listOf(
                "Prática de respiração consciente",
                "Sessões de meditação guiada (ex: Meditopia)",
                "Escrever um diário de emoções"
            ), links = listOf(
                Pair("Meditopia", "https://meditopia.com/pt"),
                Pair("Insight Timer", "https://insighttimer.com")
            ))

            Spacer(modifier = Modifier.height(16.dp))

            RecursoCard(titulo = "Conteúdos para Autoconhecimento", conteudo = listOf(
                "Podcast 'Autoconsciente'",
                "Canal Saúde Mental no YouTube",
                "Livro: Inteligência Emocional - Daniel Goleman"
            ), links = listOf(
                Pair("Podcast Autoconsciente", "https://open.spotify.com/show/3AI2xKjv4o1g8DnR5Z3WQt"),
                Pair("Canal Saúde Mental", "https://www.youtube.com/results?search_query=sa%C3%BAde+mental")
            ))
        }
    }
}

@Composable
fun RecursoCard(titulo: String, conteudo: List<String>, links: List<Pair<String, String>> = emptyList()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
        Spacer(modifier = Modifier.height(8.dp))
        conteudo.forEach {
            Text("• $it", fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp, bottom = 4.dp))
        }
        links.forEach { (nome, url) ->
            Text(
                text = nome,
                color = Color(0xFF1B5E20),
                textDecoration = TextDecoration.Underline,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 4.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecursosApoioPreview() {
    RecursosApoioScreen(navController = rememberNavController())
}
