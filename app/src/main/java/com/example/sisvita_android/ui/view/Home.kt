package com.example.sisvita_android.ui.view

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.R
import com.example.sisvita_android.navigation.AppScreen
import com.example.sisvita_android.network.RetrofitClient
import com.example.sisvita_android.ui.viewmodel.LoginViewModel
import com.example.sisvita_android.utils.UserManager
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.WindowClose
import retrofit2.Call

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    var showLogoutDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MENÚ",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.WindowClose, // Use your own logout icon here
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.sisvita_logo),
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(200.dp)
                )
            }
            item {
                InformationCard()
            }
            if (UserManager.getRol() == "Usuario") {
                item {
                    Button(onClick = { navController.navigate(AppScreen.testHome.route) }) {
                        Text(stringResource(R.string.realizar_test))
                    }
                }
                item {
                    Button(onClick = { /* TODO: Handle click */ }) {
                        Text(stringResource(R.string.consultar_resultado))
                    }
                }
                item {
                    Button(onClick = { /* TODO: Handle click */ }) {
                        Text(stringResource(R.string.recomendaciones))
                    }
                }
                item {
                    Button(onClick = { /* TODO: Handle click */ }) {
                        Text(stringResource(R.string.foro_de_discusi_n))
                    }
                }
            } else if (UserManager.getRol() == "Especialista") {
                item {
                    Button(onClick = { navController.navigate(AppScreen.vigilancia.route) }) {
                        Text(stringResource(R.string.realizar_vigilancia_))
                    }
                }
            }
        }
    }

    if (showLogoutDialog) {
        LogoutDialog(onDismiss = { showLogoutDialog = false }) {
            UserManager.clearUser()
            navController.navigate(AppScreen.login.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Cerrar sesión")
        },
        text = {
            Text(stringResource(R.string.est_s_seguro_de_que_deseas_cerrar_sesi_n))
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.confirmar_))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancelar_))
            }
        }
    )
}

@Composable
fun InformationCard(loginViewModel: LoginViewModel = viewModel()) {
    val email: String by loginViewModel.correo.observeAsState("")
    val user = UserManager.getUser()
    if (user != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(
                text = "Información",
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Nombre : " + user.nombre +
                            "\nApellidos : " + user.apellido_paterno + " " + user.apellido_materno +
                            "\nCorreo : " + user.correo_electronico +
                            "\nRol : " + UserManager.getRol(),
                    color = Color.White
                )
            }
        }
    }
}