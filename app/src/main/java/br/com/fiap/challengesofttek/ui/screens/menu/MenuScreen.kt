package br.com.fiap.challengesofttek.ui.screens.menu

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController // Necessário para o Preview funcionar com NavController

@Composable
fun MenuScreen(navController: NavController) { // Adicionado NavController
    val activity = LocalActivity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C4EC7)) // Azul FIAP/Softtek (exemplo)
            .padding(16.dp) // Padding geral um pouco menor para mais espaço interno
    ) {
        Text(
            text = "MENU PRINCIPAL",
            fontSize = 28.sp, // Um pouco maior
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp) // Espaço acima do título
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center, // Centralizar verticalmente os botões
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() // Ocupar toda a altura para melhor centralização
                .padding(horizontal = 32.dp) // Padding horizontal para os botões
        ) {
            AppMenuButton(
                text = "Avaliação de Riscos Psicossociais",
                onClick = { navController.navigate("avaliacaoRiscos") }
            )
            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento um pouco maior

            AppMenuButton(
                text = "Acompanhamento Emocional",
                onClick = { navController.navigate("acompanhamentoEmocional") }
            )
            Spacer(modifier = Modifier.height(20.dp))

            AppMenuButton(
                text = "Recursos de Apoio",
                onClick = { navController.navigate("recursosApoio") }
            )
            Spacer(modifier = Modifier.height(20.dp))

            AppMenuButton(
                text = "Visualização de Dados",
                onClick = { navController.navigate("visualizacaoDados") }
            )
            Spacer(modifier = Modifier.height(40.dp)) // Maior espaço antes do botão Sair

            AppMenuButton(
                text = "Sair",
                onClick = {
                    activity?.finish() // fecha a activity
                },
                backgroundColor = Color(0xFFE0E0E0),
                textColor = Color(0xFF2C4EC7)
            )

        }
    }
}

@Composable
fun AppMenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    textColor: Color = Color(0xFF2C4EC7) // Cor do texto padrão para os botões
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, // Cor de fundo do botão
            contentColor = textColor // Cor do conteúdo (texto/ícone)
        ),
        modifier = modifier
            .fillMaxWidth(0.9f) // Botões ocupam 90% da largura disponível na Column
            .height(56.dp) // Altura padrão para os botões
    ) {
        Text(
            text = text,
            fontSize = 16.sp, // Tamanho de fonte padronizado
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center // Centralizar texto dentro do botão
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun MenuScreenPreview() {
    // Para o Preview funcionar, precisamos de um NavController mockado ou real.
    // rememberNavController() só pode ser chamado dentro de um Composable.
    val navController = rememberNavController()
    MenuScreen(navController = navController)
}