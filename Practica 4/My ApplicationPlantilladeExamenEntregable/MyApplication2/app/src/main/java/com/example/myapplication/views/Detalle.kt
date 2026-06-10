package com.example.myapplication.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import androidx.compose.runtime.*
import com.example.myapplication.model.Coche
import com.example.myapplication.utilidades.NotificationHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.example.myapplication.componentes.TarjetaEjemplo
import com.example.myapplication.componentes.CarruselEjemplo //CARUSEL
import com.example.myapplication.componentes.CuadroDeDialogo
import com.example.myapplication.componentes.BarraDeNavegacionInferior
import com.example.myapplication.componentes.MenuDesplegable
import com.example.myapplication.componentes.BarraDeBusqueda
import com.example.myapplication.componentes.PanelLateralDeNavegacion


/*
@Composable
fun Detalle(dao: ContactDao, dni: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center){

    }
}
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detalle(
    navController: NavController,//para poder moverme
    //dao: ContactDao,
    matricula: String,
    //  matricula: String,
    modifier: Modifier = Modifier
) {
    val dbFire = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    val notificationHandler =
        NotificationHandler(context) //objeto que se encarga de mostrar notificaciones
    var coche by remember { mutableStateOf<Coche?>(null) } //// Aquí guardaremos el coche traído desde Firebase
    var showDialog by remember { mutableStateOf(true) } //empiza siendo true esto es para el caudro dialogho   import androidx.compose.runtime.* Controla si el cuadro de diálogo se ve o no.


    //pagina 23 t3
// LaunchedEffect(Unit) se ejecuta una sola vez al entrar en esta pantalla
    LaunchedEffect(Unit) {
        snapshotFlow { coche==null}
            .collect { esNulo ->
                if (esNulo)
                    return@collect
                notificationHandler.showSimpleNotification(
                    "Has entrado en Detalle",
                    "Estás viendo el detalle del coche $matricula \n usuario ${coche?.correo}  \n Pulsa aqui para volver a registro"
                )
            }
//        notificationHandler.showSimpleNotification(
//            "Has entrado en Detalle",
//            "Estás viendo el detalle del coche $matricula \n usuario $correo  \n Pulsa aqui para volver a registro"
//        )
    }


    // consulta a Firestore: busco en la colección "coches" el documento cuya ID es la matrícula

    dbFire.collection("coches").document(matricula).get()
        .addOnSuccessListener { document -> //document
            // Si  va bien convierte ese documento en un objeto Coche
            coche = Coche(
                document.get("matricula").toString(),
                document.get("marca").toString(),
                document.get("modelo").toString(),
                document.get("correo").toString(),
                document.getLong("precio")?.toInt() ?: 0
            )
            //password = document.getString("password").orEmpty()

        }.addOnFailureListener {
            //en caso de fallar mando un mensaje al usuario
            Toast.makeText(context, "El coche no se ha podido recuperar.", Toast.LENGTH_SHORT)
                .show()
        }


    if (!matricula.isNullOrEmpty()) { //Si la matrícula NO es nula ni está vacía
        dbFire.collection("coches").document(matricula)
            .get() //"Ve a Firebase Firestore y busca un coche con esta matrícula"
            .addOnSuccessListener { document ->
                if (document.exists()) {    // document.exists() comprueba si el documento realmente existe
                    coche = Coche(
                        document.get("matricula").toString(),
                        document.get("marca").toString(),
                        document.get("modelo").toString(),
                        document.get("correo").toString(),
                        document.get("precio").toString().toInt()
                        // document.get("password").toString()

                    )
                } else {
                    Log.d(
                        "Detalle",
                        "Documento no encontrado"
                    ) // Si no existe, lo escribe en el Log
                }
            }
            .addOnFailureListener { e ->
                Log.e(
                    "Detalle",
                    "Error al obtener documento",
                    e
                )   // Si ocurre un error al pedir el documento, lo escribes en el Log
            }
    } else {
        Log.e(
            "Detalle",
            "La matrícula es nula o vacía"
        ) //Si matrícula viene vacía o nula, también lo escribes en el Log
    }

//esta funcion sirve para borrar coche actual de Firestore
    val borrar: () -> Unit = borrar@{
        // Borra el documento cuyo ID es la matrícula
        dbFire.collection("coches").document(matricula).delete().addOnSuccessListener {
            Toast.makeText(context, "El contacto se ha eliminado correctamente", Toast.LENGTH_SHORT)
                .show()
            //Despues vamos a la pantalla formulario
            navController.navigate(Pantallas.Formulario.route)
        }
    }


    //panellateraldenavegacion

    PanelLateralDeNavegacion(navController = navController) {//278
        //Scaffold = estructura típica de pantalla para botones y barras

        Scaffold(

            //mi barra inferior
            bottomBar = {
                BarraDeNavegacionInferior(navController = navController)
            },
        ) { innerPadding -> //para que el scaffold no me tape el contenido

            Column(     //1ºcolumna  de pantalla la general
                modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                MenuDesplegable(navController = navController)
//MinimalDialog(onDismissRequest = { }) //funcona    con snack para ejecutar algo caundo se cierra le mensjae de dialogo        saul
                // AQUÍ sí, fuera de la zona con scroll
                //BarraDeBusqueda()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())//desliza vertical
                ) {
                    TarjetaEjemplo()
                    Card { Text(text = "Soy una tarjeta numero 2 sin más ignorame") }
                    //enseño matricula en grande
                    Text(
                        text = coche?.matricula?.uppercase() ?: "",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                    )
                    //si es nulo enseña 0 o null
                    Text(text = "Matrícula: ${coche?.matricula ?: ""}", fontSize = 16.sp)
                    Text(text = "Marca: ${coche?.marca ?: ""}", fontSize = 16.sp)
                    Text(text = "Modelo: ${coche?.modelo ?: ""}", fontSize = 16.sp)
                    Text(text = "Precio: ${coche?.precio ?: 0}", fontSize = 16.sp)
                    Text(text = "Correo: ${coche?.correo ?: ""}", fontSize = 16.sp)
                    //borra el coche
                    Button(onClick = borrar) {
                        Text("Borrar", fontSize = 16.sp)
                    }

                    CarruselEjemplo()
// Si showDialog vale true, se muestra el cuadro de diálogo
                    if (showDialog) {
                        CuadroDeDialogo(
                            // Cuando el usuario cierre el diálogo,
                            // cambiamos showDialog a false y desaparece
                            onDismissRequest = { showDialog = false
                                if(coche!=null)
                                    (coche as Coche).precio+=10

                            }

                        )
                    }
                }
            }
        }
    }
}




//menu desplegable
//https://developer.android.com/develop/ui/compose/components/drawer?hl=es-419
//panel lateral de navegación
//https://developer.android.com/develop/ui/compose/components/drawer?hl=es-419




/*
Scaffold(

    // BARRA SUPERIOR
    topBar = {
        TopAppBar(
            modifier = Modifier.height(60.dp),

            title = {
                Text(
                    text = "Registrar un usuario",
                    fontSize = 15.sp
                )
            },

            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            )
        )
    },
 */

