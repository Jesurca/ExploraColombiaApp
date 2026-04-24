package me.jesusurbinez.exploracolombiaapp


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Explore : Screen("explore", "Explorar", Icons.Default.Search)
    object Favorites : Screen("favorites", "Favoritos", Icons.Default.Favorite)
    object Profile : Screen("profile", "Perfil", Icons.Default.Person)
}

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val user = auth.currentUser
    val items = listOf(
        Screen.Home,
        Screen.Explore,
        Screen.Favorites,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFE45D25),
                            selectedTextColor = Color(0xFFE45D25),
                            indicatorColor = Color(0xFFE45D25).copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    userName = user?.displayName ?: "Usuario",
                    onLogout = {
                        auth.signOut()
                        onLogout()
                    }
                )
            }
            composable(Screen.Explore.route) { PlaceholderScreen("Pantalla de Explorar") }
            composable(Screen.Favorites.route) { PlaceholderScreen("Pantalla de Favoritos") }
            composable(Screen.Profile.route) { PlaceholderScreen("Pantalla de Perfil") }
        }
    }
}

@Composable
fun HomeScreen(userName: String, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido,",
            fontSize = 24.sp,
            color = Color.Gray
        )
        Text(
            text = userName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE45D25)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE45D25)),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}