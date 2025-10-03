package br.com.fiap.challengesofttek.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengesofttek.data.local.UserPreferences
import br.com.fiap.challengesofttek.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun obterToken() {
        viewModelScope.launch {
            _loginState.value = LoginState.Carregando
            try {
                val usuario = repository.obterToken()
                Log.d("LOGIN", "Usuário ID: ${usuario.anonymousUserId}, Token: ${usuario.token}")
                // salvar em DataStore
                userPreferences.saveUserData(usuario.token, usuario.anonymousUserId)

                // navegar para o menu
                _loginState.value = LoginState.Sucesso(usuario.anonymousUserId, usuario.token)
            } catch (e: Exception) {
                Log.e("LOGIN", "Erro ao obter token: ${e.message}", e)
                _loginState.value = LoginState.Erro(e.message ?: "Erro ao obter token")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Carregando : LoginState()
    data class Sucesso(val userId: String, val token: String) : LoginState()
    data class Erro(val mensagem: String) : LoginState()
}