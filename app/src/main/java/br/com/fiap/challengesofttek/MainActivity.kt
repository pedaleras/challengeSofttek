package br.com.fiap.challengesofttek


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengesofttek.screens.AvaliacaoRiscosScreen
import br.com.fiap.challengesofttek.screens.HumorScreen
import br.com.fiap.challengesofttek.screens.LoginScreen
import br.com.fiap.challengesofttek.screens.MenuScreen
import br.com.fiap.challengesofttek.screens.RecursosApoioScreen
import br.com.fiap.challengesofttek.screens.VisualizacaoDadosScreen
import br.com.fiap.challengesofttek.ui.theme.ChallengeSofttekTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeSofttekTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable(route = "login") { LoginScreen(navController) }
                        composable(route = "menu") { MenuScreen(navController) }
                        composable(route = "avaliacaoRiscos") { AvaliacaoRiscosScreen(navController) }
                        composable(route = "acompanhamentoEmocional") { HumorScreen(navController) }
                        composable(route = "recursosApoio") { RecursosApoioScreen(navController) }
                        composable(route = "visualizacaoDados") { VisualizacaoDadosScreen(navController) }
                    }
                }
            }
        }
    }
}