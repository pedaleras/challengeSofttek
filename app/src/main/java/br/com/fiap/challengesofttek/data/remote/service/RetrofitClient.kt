package br.com.fiap.challengesofttek.data.remote.service

import android.content.Context
import br.com.fiap.challengesofttek.data.local.UserPreferences
import br.com.fiap.challengesofttek.data.remote.interceptor.AuthInterceptor
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private lateinit var userPreferences: UserPreferences
    private lateinit var okHttpClient: OkHttpClient

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }

    fun init(context: Context) {
        userPreferences = UserPreferences(context)

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userPreferences))
            .build()
    }
}
