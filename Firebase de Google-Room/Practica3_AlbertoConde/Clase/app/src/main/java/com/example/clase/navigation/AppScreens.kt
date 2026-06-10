package com.example.clase.navigation

sealed class AppScreens (val route: String) {
    object Resultados: AppScreens("Resultados")
    object Formulario: AppScreens("Formulario")
    object Inicio: AppScreens("Inicio")
}
