package br.com.fiap.challengesofttek.util

fun calcularCategoriaFinal(mediaPercentuais: Double): String = when {
    mediaPercentuais <= 25 -> "Neutro"
    mediaPercentuais <= 50 -> "Leve"
    mediaPercentuais <= 75 -> "Moderado"
    else -> "Agudo"
}