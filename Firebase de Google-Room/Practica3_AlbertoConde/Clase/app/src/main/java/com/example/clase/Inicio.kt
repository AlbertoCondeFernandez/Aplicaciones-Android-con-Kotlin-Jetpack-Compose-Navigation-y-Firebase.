package com.example.clase

import android.content.ContentValues.TAG
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import android.widget.Toast
import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import com.example.basededatos.localdb.AppDB
import com.example.basededatos.localdb.Estructura
import com.example.basededatos.localdb.UsuarioData
import com.example.clase.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlin.compareTo
import kotlin.jvm.java
import kotlin.text.isBlank


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Inicio(navController: NavController){

    // BBDD Firebase
    val dbFirebase = Firebase.firestore // Se crea una instancia de la base de datos en la nube para consultar posteriormente los usuarios registrados en la nube y poder acceder a la colección creada.

    var email by remember { mutableStateOf("") }
    var contrasena = rememberTextFieldState("")
    var passVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Se crea una instancia de la base de datos, indicando el contexto, la clase base de datos y el nombre del archivo, permitiendo consultas en el hilo principal.
    val dbl = Room.databaseBuilder(context, AppDB::class.java, Estructura.DB.NAME)
        .allowMainThreadQueries().build()
    // La aplicación está corriendo en un hilo, pero cuando nosotros estamos almacenando los datos, estos datos corren en un hilo distinto al que está corriendo la aplicación y eso no es la mejor solución para producción.
    // Se soluciona temporalmente con .allowMainThreadQueries() y si no, es necesario realizar corrutinas o hilos en background.

    // Obtención del DAO, es decir, la interfaz para realizar operaciones CRUD sobre la tabla de la base de datos local.
    var usuarioid: UsuarioData?

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(60.dp),
                title = {
                    Text(text = "Iniciar sesión", fontSize = 15.sp)
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ){ innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bienvenid@",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))

            //Pedimos el email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    if(it.length < 30) {
                        email = it
                    }
                },
                label = { Text("Email") },
                modifier = Modifier.width(300.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            //Pedimos la contraseña
            OutlinedSecureTextField(
                state = contrasena,
                label = { Text("Contraseña") },
                modifier = Modifier.width(300.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passVisible = !passVisible }) {
                        Icon(
                            imageVector = if (passVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                textObfuscationMode = if (passVisible)
                    TextObfuscationMode.Visible
                else
                    TextObfuscationMode.RevealLastTyped
            )

            Spacer(Modifier.size(15.dp))

            lateinit var auth: FirebaseAuth
            auth = Firebase.auth

            Button(
                onClick = {
                    if (contrasena.text.isBlank() || email.isBlank()) {
                        Toast.makeText(
                            context,
                            "No puede haber campos en blanco",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        auth.signInWithEmailAndPassword(email, contrasena.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Login OK: ${auth.currentUser?.email}")
                                    navController.navigate(AppScreens.Formulario.route)
                                    Toast.makeText(
                                        context,
                                        "Usuario inició sesión",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    val ex = task.exception
                                    when (ex) {
                                        is FirebaseAuthInvalidCredentialsException -> {
                                            Toast.makeText(
                                                context,
                                                "Email o contraseña incorrectos",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        else -> {
                                            Toast.makeText(
                                                context,
                                                ex?.message ?: "Error inesperado",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }
                    }
                }
            ) {
                Text(text = "Iniciar sesión")
            }

        }
    }
}
