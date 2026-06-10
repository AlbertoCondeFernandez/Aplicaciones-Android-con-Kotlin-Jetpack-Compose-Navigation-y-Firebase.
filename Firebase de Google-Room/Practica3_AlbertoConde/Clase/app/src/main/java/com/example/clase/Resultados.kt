package com.example.clase

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.basededatos.localdb.AppDB
import com.example.basededatos.localdb.Estructura
import com.example.basededatos.localdb.UsuarioData
import androidx.room.Room
//import java.lang.reflect.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import android.widget.Toast
import androidx.compose.ui.Modifier
import kotlin.collections.toMutableList
import kotlin.jvm.java


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Resultados(navController: NavController) {
// Se obtiene el contexto actual, necesario para construir la base de datos Room y mostrar Toasts.
 val context = LocalContext.current
// Se crea una instancia de la base de datos, indicando el contexto, la clase base de datos y el nombre del archivo, permitiendo consultas en el hilo principal.
 val db = Room.databaseBuilder(context, AppDB::class.java, Estructura.DB.NAME)
     .allowMainThreadQueries().build()
// La aplicación está corriendo en un hilo, pero cuando nosotros estamos almacenando los datos, estos datos corren en un hilo distinto al que está corriendo la aplicación y eso no es la mejor solución para producción.
// Se soluciona temporalmente con .allowMainThreadQueries() y si no, es necesario realizar corrutinas o hilos en background.

// Obtención del DAO, es decir, la interfaz para realizar operaciones CRUD sobre la tabla.
 var listaUsuarios = mutableListOf<UsuarioData>()

// Ejecuta la consulta definida en el DAO que recupera todos los usuarios de la tabla.
 listaUsuarios = db.usuarioDao().getListaUsuarios().toMutableList()

// BARRA SUPERIOR
 Scaffold(
     topBar = {
         TopAppBar(
             modifier = Modifier.height(60.dp),
             title = {
         Text(text = "Listado de usuarios", fontSize = 15.sp)
     },
     colors = topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primary,
         titleContentColor = Color.White
     ),
     navigationIcon = {
         IconButton(onClick = {navController.popBackStack()
             Toast.makeText(context, "Volver atrás", Toast.LENGTH_SHORT).show()
         }
         ) {
             Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
         }
     }
         )
}) { innerPadding ->

     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(innerPadding),
         horizontalAlignment = Alignment.CenterHorizontally
     ) {

         Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(top = 10.dp)
         ) {
             Text(
                 text = "ID",
                 modifier = Modifier.weight(0.5f),
                 textAlign = TextAlign.Center
             )
             Text(
                 text = "Nombre",
                 modifier = Modifier.weight(1.5f),
                 textAlign = TextAlign.Center
             )
             Text(
                 text = "Apellidos",
                 modifier = Modifier.weight(1.5f),
                 textAlign = TextAlign.Center
             )
             Text(
                 text = "Email",
                 modifier = Modifier.weight(2.5f),
                 textAlign = TextAlign.Center
             )
             Text(
                 text = "Fecha",
                 modifier = Modifier.weight(2f),
                 textAlign = TextAlign.Center
             )
         }

         LazyColumn {
             itemsIndexed(listaUsuarios) { _, user ->
                 Row(
                     modifier = Modifier.fillMaxWidth()
                 ) {
                     Text(
                         text = "${user.idUsuario}",
                         modifier = Modifier.weight(0.5f),
                         textAlign = TextAlign.Center
                     )
                     Text(
                         text = user.nombreUsuario,
                         modifier = Modifier.weight(1.5f),
                         textAlign = TextAlign.Center
                     )
                     Text(
                         text = user.apellidosUsuario,
                         modifier = Modifier.weight(1.5f),
                         textAlign = TextAlign.Center
                     )
                     Text(
                         text = user.email,
                         modifier = Modifier.weight(2.5f),
                         textAlign = TextAlign.Center
                     )
                     Text(
                         text = user.incorporacionUsuario,
                         modifier = Modifier.weight(2f),
                         textAlign = TextAlign.Center
                     )
                 }
             }
         }

         Spacer(
             modifier = Modifier.padding(top = 30.dp)
         )

         Button(
             onClick = {
                 navController.popBackStack()
                 Toast.makeText(
                     context,
                     "Volver atrás",
                     Toast.LENGTH_SHORT
                 ).show()
             }
         ) {
             Text(text = "Volver")
         }
     }
   }
 }