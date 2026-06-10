package com.example.myapplication.views

import android.Manifest
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController

import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.myapplication.utilidades.NotificationHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU) // Sólo Android 13 o superior (API 33)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
//fun Formulario(navController: NavController, contactDao: ContactDao, modifier: Modifier = Modifier)
fun Formulario(
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    val dbFire =
        FirebaseFirestore.getInstance() //Obtiene la conexión con la base de datos de Firebase (Firestore) coche matricula marca modelo precio correo
    val fireAuth =
        FirebaseAuth.getInstance() //Obtiene el sistema de autenticación de usuarios de Firebase.
    val coleccion =
        dbFire.collection("coches") //coches de fire --Accede a la colección llamada "coches" dentro de Firestore.

    val scope =
        rememberCoroutineScope()//en caso de que nos ean efectos ej funciones que se ejecutan cuando pasa algo en el componente , osea cuando se crea y con dependencias

    val context =
        LocalContext.current //objeto que representa el entorno de la app y permite acceder a muchas cosas del sistema. como toast actividades recursos notficaciones
//    Button(     //Esto boton solo sirve apra probar si manualmente podia recibir notificaciones
//        onClick = {
//            NotificationHandler(context).showSimpleNotification(
//                "Formulario enviado", "Se guardó correctamente"
//            )
//        }) {
//        Text("Enviar")
//    }
    //// Estados de los campos del formulario
    val notificacionHander = NotificationHandler(context)
    val matricula = rememberTextFieldState("")
    val marca = rememberTextFieldState("")
    val modelo = rememberTextFieldState("")
    val precio = rememberTextFieldState("")
    val email = rememberTextFieldState("")
    val password = rememberTextFieldState("")

    var mostrarNotificacionPendiente by remember { mutableStateOf(false) }
    // Permiso de Android 13 para notificaciones.
    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS) //controla el permiso de las not

    var passVisible by remember { mutableStateOf(false) } //// Controla si enseñamos la contraseña o no
    val requiredFields = listOf(marca, modelo, precio, matricula, email, password)

    val insertarCoche: () -> Unit = ins@{           // Saca el texto escrito por el usuario
        val matricula = matricula.text.toString()
        val marca = marca.text.toString()
        val modelo = modelo.text.toString()
        val email = email.text.toString()
        val password = password.text.toString()
        val precio = try {
            precio.text.toString().toInt()      // Intentamos convertir el precio a número.
        } catch (e: NumberFormatException) {

            scope.launch {

                //inferior mensaje
                val result = snackbarHostState.showSnackbar(
                    message = "nonono",
                    actionLabel = "Borrar formulario",
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> { //el usuario pulsa el botón =borrar
                        requiredFields.forEach { field -> field.clearText() }
                    }

                    SnackbarResult.Dismissed -> { //cierra el Snackbar
                    }
                }
            }
            return@ins
        }

        // Comprobar que los campos obligatorios no están vacíos
        val allFilled = requiredFields.all { field -> !field.text.isEmpty() } // isEmpty() == ""
        if (!allFilled) {
            Toast.makeText(
                context, "ERROR: Los campos obligatorios no pueden estar vacios", Toast.LENGTH_SHORT
            ).show()
            return@ins
        }
        //Expresión regular para validar email
        val reCorreo =
            Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") //usuario válido + @ + dominio válido + . + extensión de 2+ letras
        if (!email.matches(reCorreo)) {
            Toast.makeText(
                context, "ERROR: El correo no tiene un formato válido", Toast.LENGTH_SHORT
            ).show()
            return@ins
        }

        if (password.length < 8) {
            Toast.makeText(
                context,
                "ERROR: La contraseña debe de tener al menos 8 caracteres",
                Toast.LENGTH_LONG
            ).show()
            return@ins
        }
//hago un map de documentoCoche
        val documentoCoche = mapOf(
            "matricula" to matricula,
            "marca" to marca,
            "modelo" to modelo,
            "precio" to precio,
            "correo" to email,
            // "password" to password
        )




        try {

            //AUTENTIFICACIÓN
            fireAuth.createUserWithEmailAndPassword(
                email,
                password
            )    //crea el usuario en Firebase Authentication.
                .addOnSuccessListener { //meto en fire                    //sin el otro me daba problemas de columna en innicio sesión preguntar a saul
                    fireAuth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                        if (postNotificationPermission.status.isGranted) {
                            notificacionHander.showSimpleNotification(
                                "Aviso",
                                "Se ha enviado un correo a tu dirección para que confirmes tu cuenta"
                            )
                        } else {
                            //mostrarNotificacionPendiente = true
                            postNotificationPermission.launchPermissionRequest() //con esto pides permiso a la notificacion
                        }//a la vista nada más crearle me meto a lsitado nuve
                        navController.navigate(Pantallas.ListadoNube.route)
                        //navController.navigate(Pantallas.InicioSesion.route)
                    }

                    Toast.makeText(context, "Usuario guardado en la nube", Toast.LENGTH_SHORT)
                        .show()
                }
            // Después guardamos el coche en Firestore.
            coleccion.document(matricula)     //uso email como id
                .set(documentoCoche) //documentoCoche creas si n existe y actualiza si eexiste get es obtener y  recuperar pero set es guardar/actualizar
                .addOnSuccessListener {
                    Toast.makeText(context, "Información guardada en la nube", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "ERROR: No se pudo guardar la información en la nube",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            // Limpiamos el formulario
            requiredFields.forEach { field -> field.clearText() }
        } catch (e: SQLiteConstraintException) {//Exception capturaria cualquier excapcion
            Toast.makeText(context, "ERROR: Ya existe un contacto con ese DNI", Toast.LENGTH_SHORT)
                .show()
        }
    }

    val mostrarListado: () -> Unit = {
        navController.navigate(Pantallas.ListadoNube.route)
    }

    val mostrarInicioSesion: () -> Unit = {
        navController.navigate(Pantallas.InicioSesion.route)
    }
//Al entrar en la pantalla, pedimos el permiso de notificaciones si falta
    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }


    Column(

        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),//importamos ambas cosas
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            state = matricula, label = { Text("Matricula *") })
        OutlinedTextField(
            state = marca, label = { Text("Marca *") })
        OutlinedTextField(
            state = modelo, label = { Text("Modelo *") })
        OutlinedTextField(
            state = precio,  //   venta de coches
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Precio *") })
        OutlinedTextField(
            state = email, label = { Text("Correo *") })
        OutlinedSecureTextField(
            state = password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text("Contraseña *") },
            trailingIcon = {
                IconButton(onClick = { passVisible = !passVisible }) {
                    Icon(
                        imageVector = if (passVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            textObfuscationMode = if (passVisible) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped //tema 2 pag 8
        )

        Button(onClick = insertarCoche) {
            Text("Registrar")
        }
        Button(onClick = mostrarListado) {
            Text("Mostrar contactos")
        }
        Button(onClick = mostrarInicioSesion) {
            Text("Autenticarse")
        }

    }
}


/*
//permisod e notificacion
LaunchedEffect(key1 = true) { // Al cargar la ventana pide permiso POST_NOTIFICATIONS si no se pidió. Sólo
//la primera vez en la primera recomposición. Pide el permiso automáticamente.
if (!postNotificationPermission.status.isGranted) {
    postNotificationPermission.launchPermissionRequest() // Popup de permiso si no está concedido para
  //  que el usuario acepte o rechace
}
}
 */