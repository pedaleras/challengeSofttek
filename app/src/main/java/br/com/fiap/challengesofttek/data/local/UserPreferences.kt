package br.com.fiap.challengesofttek.data.local

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    suspend fun saveUserData(token: String, userId: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_ID_KEY] = userId
        }
    }

    fun getUserData(): Flow<Pair<String?, String?>> {
        return context.dataStore.data.map { prefs ->
            Pair(prefs[TOKEN_KEY], prefs[USER_ID_KEY])
        }
    }
}