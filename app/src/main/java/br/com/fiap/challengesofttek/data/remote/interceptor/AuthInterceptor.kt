package br.com.fiap.challengesofttek.data.remote.interceptor

import br.com.fiap.challengesofttek.data.local.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.runBlocking

class AuthInterceptor(private val userPreferences: UserPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Ler token do DataStore de forma síncrona
        val token = runBlocking {
            userPreferences.getUserData().map { it.first }.firstOrNull()
        }

        val request = chain.request().newBuilder()
        if (!token.isNullOrEmpty()) {
            request.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}
