package br.com.fiap.challengesofttek.ui.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengesofttek.data.local.UserPreferences
import br.com.fiap.challengesofttek.data.repository.LoginRepository
import br.com.fiap.challengesofttek.data.remote.service.RetrofitClient

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = run {
        val context = LocalContext.current.applicationContext
        viewModel(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = LoginRepository(RetrofitClient.api)
                val userPreferences = UserPreferences(context)
                return LoginViewModel(repository, userPreferences) as T
            }
        })
    }
) {
    val loginState by viewModel.loginState.collectAsState()

    // Navega quando der sucesso
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Sucesso) {
            navController.navigate("menu") {
                popUpTo("login") { inclusive = true } // remove login da pilha
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFED145B))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Bem-vindo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botão com loading integrado
            Button(
                onClick = { viewModel.obterToken() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFFED145B)
                ),
                enabled = loginState !is LoginState.Carregando, // desativa quando carregando
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (loginState is LoginState.Carregando) {
                    CircularProgressIndicator(
                        color = Color(0xFFED145B),
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(20.dp) // tamanho fixo pequeno
                    )
                } else {
                    Text(
                        text = "Entrar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Mensagem de erro
            if (loginState is LoginState.Erro) {
                val mensagem = (loginState as LoginState.Erro).mensagem
                Text(
                    text = "Erro: $mensagem",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}
