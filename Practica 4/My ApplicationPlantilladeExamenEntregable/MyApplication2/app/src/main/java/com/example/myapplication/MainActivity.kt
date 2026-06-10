package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.componentes.PanelLateralDeNavegacion
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.views.Formulario
import com.example.myapplication.views.Detalle
import com.example.myapplication.views.InicioSesion
import com.example.myapplication.views.Pantallas
import com.example.myapplication.views.ListadoNube

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            MyApplicationTheme {
                AppShell()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() { //abstrae objeto navcontroller y scafoold
    val navController = rememberNavController()//cera objeto de navegación
//
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

//    val tituloPantalla = when (currentRoute) {
//        Pantallas.Formulario.route -> Pantallas.Formulario.titulo
//        Pantallas.InicioSesion.route -> Pantallas.InicioSesion.titulo
//        Pantallas.ListadoNube.route -> Pantallas.ListadoNube.titulo
//        Pantallas.Detalle.route -> Pantallas.Detalle.titulo
//        else -> "Mi aplicación"
//    }
    val tituloPantalla = when {
        currentRoute == Pantallas.Formulario.route -> Pantallas.Formulario.titulo
        currentRoute == Pantallas.InicioSesion.route -> Pantallas.InicioSesion.titulo
        currentRoute == Pantallas.ListadoNube.route -> Pantallas.ListadoNube.titulo
        // currentRoute == Pantallas.Detalle.route -> Pantallas.Detalle.titulo
        currentRoute?.startsWith("detalle/") == true -> Pantallas.Detalle("").titulo
        else -> "Mi aplicación"
    }



    PanelLateralDeNavegacion(navController = navController) {   //si voy a navegar es necesariod e usar


        val snackbarHostState = remember { SnackbarHostState() }            //ver que ahce pag 10

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            //Aquí es donde vas a dibujar los Snackbars, y usa este estado para controlarlo

            topBar = {
                TopAppBar(
                    modifier = Modifier.height(80.dp),
//                    title = {
//                        Text(
//                            text = currentRoute ?: "",
//                            fontSize = 15.sp
//                        )
//                    },//como poner titulo personalizado en cada ruta
                    title = { Text(text = tituloPantalla, fontSize = 15.sp) },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            AppNav(Modifier.padding(innerPadding), navController, snackbarHostState)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNav(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState        //se lo paso a pantalla para puedan usar el mismo SnackbarHostState
) {
    val context = LocalContext.current
    BackHandler(true) { Toast.makeText(context, "no puedes ir atras", Toast.LENGTH_SHORT).show() }


    NavHost(
        navController,
        startDestination = Pantallas.Formulario.route
    ) {//creo navh por ente las rutas
        composable(route = Pantallas.Formulario.route) {
            Formulario(navController, modifier, snackbarHostState)
        }

        composable(route = Pantallas.InicioSesion.route) {
            InicioSesion(navController, modifier, snackbarHostState)
        }

//        composable (route = Pantallas.Listado.route) {
//            Listado(navController, modifier)
//        }

        composable(route = Pantallas.ListadoNube.route) {
            ListadoNube(navController, modifier, snackbarHostState)
        }

        composable(
            // route = "detalle/{matricula}", //Pantallas.Detalle("{matricula}").route
            route = Pantallas.Detalle("{matricula}").route,
            arguments = listOf(
                navArgument("matricula") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val matriculaArgument = backStackEntry.arguments?.getString("matricula") ?: ""
//Porque eso no es la matrícula en sí desde el principio, sino el valor que llega como argumento de navegación.
            Detalle(navController, matriculaArgument, modifier)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        AppShell()
    }
}
