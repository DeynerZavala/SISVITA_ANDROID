package com.example.sisvita_android.ui.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.R
import com.example.sisvita_android.navigation.AppScreen
import com.example.sisvita_android.ui.viewmodel.LoginViewModel
import kotlin.math.log

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Login(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val loginResult by loginViewModel.loginResult.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(loginResult) {
        loginResult?.let {
            if (it.message == "Inicio de sesión exitoso") {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                navController.navigate(AppScreen.testHome.route)
            }
            else if (it.message == "Credenciales inválidas"){
                Toast.makeText(context, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sisvita_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.KeyboardArrowDown
                else
                    Icons.Filled.KeyboardArrowUp

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.login(email, password)



                } else {
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "INICIAR SESIÓN")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { /* Navegar a pantalla de registro */ }) {
            Text("¿No tiene cuenta? Registrarse")
        }
    }
}
