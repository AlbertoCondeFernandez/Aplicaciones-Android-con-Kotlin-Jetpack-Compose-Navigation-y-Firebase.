package com.example.myapplication.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.model.Coche
import com.google.firebase.firestore.FirebaseFirestore


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.utilidades.NotificationHandler

@Composable
fun ListadoNube(
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {

    val dbFire = FirebaseFirestore.getInstance()
    val list = remember { mutableStateListOf<Coche>() } // Lista reactiva se actualzia cuando cmabia

// Mostramos un Snackbar nada más entrar.
    LaunchedEffect(Unit) {
        val result = snackbarHostState.showSnackbar(
            message = "caceres",
            actionLabel = "Ir a formulario",
            withDismissAction = true,
            duration = SnackbarDuration.Indefinite
        )

        when (result) {
            SnackbarResult.ActionPerformed -> {
                navController.navigate(Pantallas.Formulario.route)
            }

            SnackbarResult.Dismissed -> {
            }
        }
    }


//el coche por itemds //obtener /Pedimos todos los coches a Firestore
    dbFire.collection("coches").get()
        .addOnSuccessListener { data ->

            println(data)//Sirve para imprimir en la consola/log lo que ha devuelto Firebase en data.

            list.clear()

            for (document in data.documents) {

                val coche = Coche(
                    document.get("matricula").toString(),
                    document.get("marca").toString(),
                    document.get("modelo").toString(),
                    document.get("correo").toString(),
                    document.getLong("precio")?.toInt() ?: 0,
                    //document.get("password").toString()
                )
                list.add(coche)
            }
        }
        .addOnFailureListener { e ->
            println("Error al obtener firebase: " + e.message)
        }

//    val mostrarDetalle: (String) -> Unit = { matricula  ->
//        navController.navigate(Pantallas.Detalle(matricula ).route)
//    }

    //Lo que se ve
    val mostrarDetalle: (String) -> Unit = { matricula ->
        navController.navigate("detalle/$matricula")
    }

    LazyColumn(modifier) {
        items(list) { item ->

//            Button(
//                onClick = { mostrarDetalle(item.matricula) },
//                modifier = Modifier.border(1.dp, Color.Black, RectangleShape)
//            ) {

            Row(
                Modifier
                    .padding(15.dp)
                    .clickable(onClick = { mostrarDetalle(item.matricula) })
                    .fillMaxWidth(),
                Arrangement.spacedBy(5.dp)
            ) {

                Text(item.correo, fontSize = 16.sp)
                Text(item.matricula, fontSize = 16.sp)
//                    Text(item.marca, fontSize = 16.sp)
//                    Text(item.modelo, fontSize = 16.sp)

            }
            HorizontalDivider()//con estas rallas separo
        }
    }
}


//val context = LocalContext.current
//val notificationHandler = NotificationHandler(context)

//    LaunchedEffect(Unit) {
//        snackbarHostState
//            .showSnackbar(
//                message = "Ejemplo de mensaje",
//                actionLabel = "Hacer algo",
//                withDismissAction = true, // Para visualizar la X de cierre
//// Por defecto es SnackbarDuration.Short
//                duration = SnackbarDuration.Indefinite
//            )
//    }

//SnackbarHostState


//luego lo miro no funicona notificación barra
//val snackbarHostState = remember { SnackbarHostState() }