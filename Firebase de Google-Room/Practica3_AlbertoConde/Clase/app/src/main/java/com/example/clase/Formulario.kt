package com.example.clase

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.room.Room
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import com.example.basededatos.localdb.AppDB
import com.example.basededatos.localdb.Estructura
import com.example.basededatos.localdb.UsuarioData
import com.example.basededatos.localdb.UsuarioDao
import com.example.clase.navigation.AppScreens
import com.example.clase.Resultados

//import com.example.basededatos.navigation.AppScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(navController: NavController) {

    // Variables de los datos recogidos en los formularios
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var incorporacion by remember { mutableStateOf("") }
    var contrasena = rememberTextFieldState("")
    var passVisible by remember { mutableStateOf(false) }
    val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

    // Contexto
    val context = LocalContext.current

    // Base de datos Room
    val db = Room.databaseBuilder(
        context,
        AppDB::class.java,
        Estructura.DB.NAME
    ).allowMainThreadQueries().build()

    val usuarioD: UsuarioDao = db.usuarioDao()
    var usuarioem: UsuarioData?

    val dbfire = FirebaseFirestore.getInstance()

    // BARRA SUPERIOR
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(60.dp),
                title = {
                    Text(text = "Registrar un usuario", fontSize = 15.sp)
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    if (it.length < 40) nombre = it
                },
                label = { Text("Nombre del usuario") },
                modifier = Modifier.width(300.dp)
            )

            // Apellidos
            OutlinedTextField(
                value = apellidos,
                onValueChange = {
                    if (it.length < 50) apellidos = it
                },
                label = { Text("Apellidos del usuario") },
                modifier = Modifier.width(300.dp)
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    if (it.length < 40) email = it
                },
                label = { Text("Email") },
                modifier = Modifier.width(300.dp)
            )

            // Contraseña
            OutlinedSecureTextField(
                state = contrasena,
                label = { Text("Contraseña") },
                modifier = Modifier.width(300.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    IconButton(onClick = { passVisible = !passVisible }) {
                        Icon(
                            imageVector = if (passVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = if (passVisible)
                                "Ocultar contraseña"
                            else
                                "Mostrar contraseña"
                        )
                    }
                },
                textObfuscationMode = if (passVisible)
                    TextObfuscationMode.Visible
                else
                    TextObfuscationMode.RevealLastTyped
            )

            // Incorporación
            OutlinedTextField(
                value = incorporacion,
                onValueChange = { incorporacion = it },
                label = { Text("Incorporación del usuario") },
                modifier = Modifier.width(300.dp)
            )

            Spacer(modifier = Modifier.size(20.dp))


            lateinit var auth: FirebaseAuth
            auth = Firebase.auth


            // BOTÓN AGREGAR USUARIO
            Button(
                onClick = {
                    when {
                        nombre.isBlank() -> {
                            Toast.makeText(context, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                        }

                        apellidos.isBlank() -> {
                            Toast.makeText(context, "Los apellidos no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                        }

                        email.isBlank() -> {
                            Toast.makeText(context, "El email no puede estar vacío", Toast.LENGTH_SHORT).show()
                        }

                        !email.matches(emailPattern) -> {
                            Toast.makeText(context, "El email no tiene un formato válido", Toast.LENGTH_SHORT).show()
                        }

                        contrasena.text.length <= 8 -> {
                            Toast.makeText(
                                context,
                                "La contraseña debe tener al menos 8 caracteres",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        incorporacion.isBlank() -> {
                            Toast.makeText(
                                context,
                                "La fecha de incorporación no puede estar vacía",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            auth.createUserWithEmailAndPassword(email, contrasena.text.toString())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                                        val usr = UsuarioData(
                                            nombreUsuario = nombre,
                                            apellidosUsuario = apellidos,
                                            incorporacionUsuario = incorporacion,
                                            email = email
                                        )
                                        usuarioD.nuevoUsuario(usr)

                                        val data = mapOf(
                                            "nombre" to nombre,
                                            "apellidos" to apellidos,
                                            "email" to email,
                                            "incorporacion" to incorporacion
                                        )

                                        dbfire.collection("usuarios")
                                            .document(uid)
                                            .set(data)
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    context,
                                                    "Usuario registrado correctamente",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate(AppScreens.Resultados.route)
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(
                                                    context,
                                                    "Error de Firestore: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    } else {
                                        Log.e("Registro", "Firebase error: ${task.exception?.message}")
                                        Toast.makeText(
                                            context,
                                            "Error Firebase: ${task.exception?.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    }
                }
            ) {
                Text(text = "Agregar usuario")
            }



            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Ir a resultados",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(route = AppScreens.Resultados.route)
                }
            ) {
                Text(text = "Ir a resultados")
            }
        }
    }
}





