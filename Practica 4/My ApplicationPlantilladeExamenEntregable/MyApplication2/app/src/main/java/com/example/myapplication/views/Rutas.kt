/*
sealed class Pantallas(val route: String, val titulo: String) {
    //solo son nom,bres de rutas , no navego pero lo uso para navegar
    // Es equivalente lo siguiente:
    // - navController.navigate(Pantallas.Formulario.route)
    // - navController.navigate("formulario")
    object Formulario: Pantallas("formulario" ,
        titulo = "Formulario de coches")
    object InicioSesion: Pantallas(
        "logear",
        titulo = "Inicio de sesión")
    //object Listado: Pantallas("listado")
    object ListadoNube: Pantallas("listadoNube",
        titulo = "Listado de coches")
    data class Detalle(val matrícula: String) : Pantallas("Detalle/$matrícula",
        titulo = "Detalle del coche") // main 140Pantallas.Detalle("{matricula}").route, 140 preguntar a saul
// data class Detalle(val matricula: String) : Pantallas("Detalle/$matricula")
}
ya no es una data class que reciba parámetros.

//cuando las rutas cambien denombre podran con esto
*/

package com.example.myapplication.views

sealed class Pantallas(val route: String, val titulo: String) {

    object Formulario : Pantallas("formulario", "Formulario")

    object InicioSesion : Pantallas("logear", "Inicio de sesión")

    object ListadoNube : Pantallas("listadoNube", "Listado en la nube")

//   object Detalle : Pantallas("detalle/{matricula}", "Detalle del coche")
data class Detalle(val matrícula: String) : Pantallas("detalle/$matrícula",
    titulo = "Detalle del coche")
}

