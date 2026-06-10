package com.example.myapplication.views

import android.content.Context
import android.content.ContextWrapper
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth

import kotlin.system.exitProcess

@Composable
fun InicioSesion(
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {

    val authFire = FirebaseAuth.getInstance() //obtiene el sistema de autenticación de Firebase
    //authFire.createUserWithEmailAndPassword(email, password)    //registrar un usuario
    //authFire.signInWithEmailAndPassword(email, password)  //iniciar sesión
    //authFire.currentUser?.sendEmailVerification()     //Enviar verificación de correo
    //authFire.currentUser                              //authFire.currentUser
    val context = LocalContext.current
    val email = rememberTextFieldState("")
    val password = rememberTextFieldState("")
    var passVisible by remember { mutableStateOf(false) }
    val requiredFields = listOf(email, password)

    //Nada más entrar sale
    LaunchedEffect(Unit) {
        val result = snackbarHostState.showSnackbar(
            message = "Cierra la apli",
            actionLabel = "se cierra",
            withDismissAction = true,
            duration = SnackbarDuration.Indefinite
        )

        when (result) {
            SnackbarResult.ActionPerformed -> {
                context.findActivity()
                    ?.finishAffinity() //Cierra la Activity actual y también las relacionadas de tu app.
                exitProcess(0)
            }

            SnackbarResult.Dismissed -> {//usuario cierra not
            }
        }
    }


// Creamos una función (lambda) llamada logear.
// Es una función que no recibe parámetros y no devuelve nada.
// Se ejecutará cuando pulses el botón de iniciar sesion
    val logear: () -> Unit = log@{

        // Sacamos el texto que el usuario ha escrito en los campos
        // email.text es el contenido del TextField
        // toString() lo convierte en un String normal
        val email = email.text.toString()

        val password = password.text.toString()


        // requiredFields es una lista que contiene los campos obligatorios
        // .all significa: comprobar que TODOS  los campos del formulario estan rellenos nada vacio
        val allFilled = requiredFields.all { field -> !field.text.isEmpty() }

        // Si alguno está vacio entra aquí
        if (!allFilled) {
            // Mostramos un mensaje pequeño en pantalla
            Toast.makeText(
                context,
                "ERROR: Los campos obligatorios no pueden estar vacíos",
                Toast.LENGTH_SHORT
            ).show()

            // return@log significa:
            // salir de esta función inmediatamente y no seguir ejecutando el login
            return@log
        }


        // Creamos una expresión regular (Regex)
        // Esto sirve para comprobar si el correo tiene formato correcto
        // ejemplo correcto: ejemplo@email.com
        val reCorreo = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

        // Si el email no coincide con el patrón válido
        if (!email.matches(reCorreo)) {

            Toast.makeText(
                context,
                "ERROR: El correo no tiene un formato válido",
                Toast.LENGTH_SHORT
            ).show()

            // salimos de la función
            return@log
        }

        // Comprobamos que la contraseña tenga al menos 8 caracteres
        if (password.length < 8) {

            Toast.makeText(
                context,
                "ERROR: La contraseña debe tener al menos 8 caracteres",
                Toast.LENGTH_SHORT
            ).show()

            return@log
        }


        // Aquí ya pasamos a intentar iniciar sesión en Firebase Authentication
        // Firebase comprobará si el email y la contraseña existen en su sistema
        authFire.signInWithEmailAndPassword(email, password)

            // Si el login funciona correctamente
            .addOnSuccessListener {

                // Navegamos a la pantalla del listado
                navController.navigate(Pantallas.ListadoNube.route)

                // Mostramos mensaje de éxito
                Toast.makeText(
                    context,
                    "El usuario inició sesión",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Si el login falla (correo incorrecto, contraseña incorrecta, usuario no existe, etc.)
            .addOnFailureListener { e ->

                Toast.makeText(
                    context,
                    // Mostramos el error que devuelve Firebase
                    "Hubo un problema al iniciar sesión: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }


        // Cuando termina todo, limpiamos los campos del formulario
        // Recorremos cada campo obligatorio
        requiredFields.forEach { field ->

            // borramos el texto que había escrito el usuario
            field.clearText()
        }
    }

    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            state = email,
            label = { Text("Correo *") }
        )
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
            textObfuscationMode = if (passVisible) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped
        )

        Button(onClick = logear) {
            Text("Iniciar sesión")
        }
        //Contacts(if (contactDao == null) listOf() else contactDao.getAll())

        //con esto se podría ver


        //Para ver el formulario
        val mostrarFormulario: () -> Unit = {
            navController.navigate(Pantallas.Formulario.route)
        }

        Button(onClick = mostrarFormulario) {
            Text("mostrar Formulario")
        }

    }
}
//sirve para encontrar la Activity actual a partir de un Context , gracias a eso puedo cerrarlo
private fun Context.findActivity(): ComponentActivity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is ComponentActivity) return ctx
        ctx = ctx.baseContext
    }
    return null
}
