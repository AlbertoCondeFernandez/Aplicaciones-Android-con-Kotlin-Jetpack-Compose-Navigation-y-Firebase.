package com.example.myapplication.componentes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.myapplication.R

import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.composables.icons.lucide.HeartPulse
import com.composables.icons.lucide.Lucide
import com.example.myapplication.utilidades.FontAwesomeCity

import com.example.myapplication.views.Pantallas
import androidx.compose.ui.platform.LocalContext
import android.app.Activity

data class ElementoCarrusel(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val descripcion: String
)

@Composable
fun TarjetaEjemplo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Tarjeta principal",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Este componente muestra contenido agrupado dentro de una tarjeta."
            )
        }
    }
}

@Composable
fun CuadroDeDialogo(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Esto es un cuadro de diálogo miooo",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CarruselEjemplo() {
    val elementos = remember {
        listOf(
            ElementoCarrusel(0, R.drawable._6452027893540, "Magdalena"),
            ElementoCarrusel(1, R.drawable.foxesworld_1, "Dona"),
            ElementoCarrusel(2, R.drawable.istockphoto_1004792742_612x612, "Pastel"),
            ElementoCarrusel(
                3,
                R.drawable.planeo_de_un_aguila_calva_7b6b9957_240618121151_1280x854,
                "Águila"
            ),
            ElementoCarrusel(4, R.drawable.vistaap, "Paisaje")
        )
    }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { elementos.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { indice ->
        val elemento = elementos[indice]
        Image(
            painter = painterResource(id = elemento.imageResId),
            contentDescription = elemento.descripcion,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(205.dp)
                .clip(MaterialTheme.shapes.extraLarge)
        )
    }
}


@Composable
fun MenuDesplegable(navController: NavController) {

    val context = LocalContext.current //con contex puedo hacer casi de todo pro ejemplo lo uso aqui para cerrar
    var expandido by remember { mutableStateOf(false) }

    val opciones = listOf(
        "Inicio-sesion",
        "Listado",
        "Formualario",
        "Cerrar"
    )

    Box(
        modifier = Modifier.padding(16.dp)
    ) {

        IconButton(onClick = { expandido = !expandido }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Abrir menú desplegable"
            )
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {

            opciones.forEach { opcion ->

                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {

                        expandido = false

                        when (opcion) {

                            "Inicio-sesion" -> navController.navigate(Pantallas.InicioSesion.route)
                            "Listado" -> navController.navigate(Pantallas.ListadoNube.route)
                            "Formualario" -> navController.navigate(Pantallas.Formulario.route)

                            "Cerrar" -> {
                                navController.navigate(Pantallas.InicioSesion.route)
                                (context as? Activity)?.finishAffinity()
                            }
                        }
                    }
                )

            }
        }
    }
}
//context.findActivity()?.finishAffinity() cerrrar


@Composable
fun BarraDeNavegacionInferior(navController: NavController) {
    //var elementoSeleccionado by remember { mutableStateOf(0) }
//no creo que lo vallamos a usar                                    saul
//    val elementos = listOf(
//        "Inicio" to Icons.Default.Home,
//        "Favoritos" to Icons.Default.Star,
//        "Ajustes" to Icons.Default.Settings
//    )
    NavigationBar {//como saber que son navigation item
        // Botón de volver atrás
//        Button( //forma de boton
//            onClick = { navController.popBackStack() }
//        ) {
//            Text("Atrábichos")
//        }

        NavigationBarItem(
            selected = false,
            onClick = { navController.popBackStack() },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Atrás"
                )
            },
            label = { Text("Atrás se va") }
        )


        // Botón para ir a formulario
        NavigationBarItem(
            selected = false,                                                   //no esta selecionado pero preguntar a saul
            onClick = {
                navController.navigate(Pantallas.Formulario.route)
            },
            icon = {
                Icon( //imageVector = Icons.Filled.Calculate,
                    //imageVector = Icons.Filled.Calculate,
                   // imageVector = Lucide.HeartPulse,
                    imageVector = FontAwesomeCity,
                    contentDescription = "Volver a formulario "
                )
            },
            label = { Text("Formulario coches") }
        )
//        NavigationBarItem(
//            selected = false,
//            onClick = {
//                navController.navigate(Pantallas.Formulario.route)
//            },
//            icon = {}, // vacío
//            label = { Text("brazil") }
//        )


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanelLateralDeNavegacion(
    navController: NavController, ///import para poder navegar
    contenido: @Composable () -> Unit
) {
    val estadoDrawer = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val piladenavgecaión by navController.currentBackStackEntryAsState() //es como un historial de navegación  navBackStackEntry
    val currentRoute = piladenavgecaión?.destination?.route
    
    if (currentRoute != Pantallas.Formulario.route) {
        return contenido()
    }

    ModalNavigationDrawer(
        drawerState = estadoDrawer,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Panel lateral de navegación",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                NavigationDrawerItem(
                    label = { Text("Inicio sesión") },
                    selected = true,
                    onClick = {
                        navController.navigate(Pantallas.InicioSesion.route)
                        scope.launch { estadoDrawer.close() } },
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = "Inicio",
                            tint = Color(0xFFFF0000) //codigo hex
                        )

                    }
                )

                NavigationDrawerItem(
                    label = { Text("Favoritos listado") },
                    selected = false,
                    onClick = {
                        navController.navigate(Pantallas.ListadoNube.route)
                        scope.launch { estadoDrawer.close() } },
                    icon = {
                        Icon(Icons.Default.Star, contentDescription = "Favoritos",
                           // tint = Color.Red //se importa color
                            tint = Color(0xFFC7142F) //C7142F https://htmlcolorcodes.com/es/ código hex

                        )
                    }
                )

                   // navController.navigate(Pantallas.InicioSesion.route)

                NavigationDrawerItem(
                    label = { Text("Ajustes FORMULARIO") },
                    selected = false,
                    onClick = { scope.launch { estadoDrawer.close() }
                        navController.navigate(Pantallas.Formulario.route)
                              },
                    icon = {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes",
                            tint = Color(0xFF4CAF50))//codigo hex
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Ejemplo de panel lateral") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { estadoDrawer.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir panel lateral"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                contenido()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraDeBusqueda() {

    // Texto que escribe el usuario
    var texto by remember { mutableStateOf("") }

    // Si la barra está abierta o cerrada
    var activa by remember { mutableStateOf(false) }

    // Lista de ejemplo donde buscar
    val sugerencias = listOf(
        "Android",
        "Jetpack Compose",
        "Material 3",
        "Carrusel",
        "Barra de búsqueda"
    )

    SearchBar(

        // estilo de la barra
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        // texto actual del buscador
        query = texto,

        // cuando el usuario escribe algo
        onQueryChange = { texto = it },

        // cuando pulsa buscar
        onSearch = { activa = false },

        // si la barra está abierta
        active = activa,

        // cambiar estado abierto/cerrado
        onActiveChange = { activa = it },

        // texto que aparece cuando está vacío
        placeholder = { Text("Buscar...") },

        // icono de la lupa
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        }
    ) {

        // filtramos sugerencias según lo escrito
        val resultados = sugerencias.filter {
            it.contains(texto, ignoreCase = true)
        }

        // mostramos resultados
        resultados.forEach { resultado ->

            ListItem(
                headlineContent = { Text(resultado) },

                leadingContent = {
                    Icon(Icons.Default.Search, contentDescription = resultado)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeslizarParaActualizar() {
    var refrescando by remember { mutableStateOf(false) }
    var elementos by remember {
        mutableStateOf(
            listOf("Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4")
        )
    }

    val scope = rememberCoroutineScope()

    PullToRefreshBox(
        isRefreshing = refrescando,
        onRefresh = {
            scope.launch {
                refrescando = true
                delay(1500)
                elementos = elementos.shuffled()
                refrescando = false
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(elementos) { elemento ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Actualizar"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = elemento)
                    }
                }
            }
        }
    }
}
/*
@Composable
fun DeslizarParaActualizar(
    modifier: Modifier = Modifier,
    onRefresh: suspend () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    // Estado del pull-to-refresh
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            // Lanzamos la acción de refresco en una coroutine
            scope.launch {
                refreshing = true
                onRefresh()
                // Aseguramos un mínimo visual de 500ms para que el indicador sea visible
                delay(500)
                refreshing = false
            }
        }
    )

    Box(modifier = modifier.fillMaxSize()) {
        PullRefreshBox(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }

        // Indicador en la parte superior
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
        )
    }
}
*/