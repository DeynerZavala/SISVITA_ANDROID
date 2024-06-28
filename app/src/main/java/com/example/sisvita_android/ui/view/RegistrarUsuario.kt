package com.example.sisvita_android.ui.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.R
import com.example.sisvita_android.data.model.TituloData
import com.example.sisvita_android.navigation.AppScreen
import com.example.sisvita_android.ui.viewmodel.RegistrarUsuarioViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft
import compose.icons.fontawesomeicons.solid.Eye
import kotlinx.coroutines.launch

import compose.icons.fontawesomeicons.solid.EyeSlash
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrarUsuario(navController: NavController, registrarUsuarioViewModel: RegistrarUsuarioViewModel = viewModel()) {
    val nombre by registrarUsuarioViewModel.nombre.observeAsState("")
    val apellidoPaterno by registrarUsuarioViewModel.apellidoPaterno.observeAsState("")
    val apellidoMaterno by registrarUsuarioViewModel.apellidoMaterno.observeAsState("")
    val correo by registrarUsuarioViewModel.correo.observeAsState("")
    val contrasena by registrarUsuarioViewModel.contrasena.observeAsState("")
    val confirmarContrasena by registrarUsuarioViewModel.confirmarContrasena.observeAsState("")
    val correoValido by registrarUsuarioViewModel.correoValido.observeAsState(true)
    val ubigeo by registrarUsuarioViewModel.ubigeo.observeAsState("")
    var contrasenaVisible by remember { mutableStateOf(false) }
    var confirmarContrasenaVisible by remember { mutableStateOf(false) }
    val registroValido by registrarUsuarioViewModel.registroValido.observeAsState(false)
    val mensajeResult by registrarUsuarioViewModel.mensajeResult.observeAsState("")
    val contrasenaValido: Boolean by registrarUsuarioViewModel.contrasenaValido.observeAsState(false)
    val roles = registrarUsuarioViewModel.roles
    val selectedRole by registrarUsuarioViewModel.selectedRole.observeAsState(roles[0])
    val context = LocalContext.current

    val dropdownItems by registrarUsuarioViewModel.dropdownItems.observeAsState(emptyList())
    val selectedDropdownItem by registrarUsuarioViewModel.selectedDropdownItem.observeAsState()

    LaunchedEffect(Unit) {
        launch {
            registrarUsuarioViewModel.iniciarTitulo()
        }
    }
    LaunchedEffect(registroValido) {
        if (registroValido == true) {
            Toast.makeText(context, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
            registrarUsuarioViewModel.onRegistroValidoChange()
            navController.navigate(AppScreen.login.route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Start)
        ) {
            Icon(imageVector = FontAwesomeIcons.Solid.ArrowLeft, contentDescription = null, modifier = Modifier.size(24.dp))
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.sisvita_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("SISVITA", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { registrarUsuarioViewModel.onNombreChange(it) },
                    label = { Text("Nombre") },
                    placeholder = { Text("Ingrese su nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = apellidoPaterno,
                    onValueChange = { registrarUsuarioViewModel.onApellidoPaternoChange(it) },
                    label = { Text("Apellido Paterno") },
                    placeholder = { Text("Ingrese su apellido paterno") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = apellidoMaterno,
                    onValueChange = { registrarUsuarioViewModel.onApellidoMaternoChange(it) },
                    label = { Text("Apellido Materno") },
                    placeholder = { Text("Ingrese su apellido materno") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = correo,
                    onValueChange = { registrarUsuarioViewModel.onCorreoChange(it) },
                    label = { Text("Correo") },
                    placeholder = { Text("Ingrese su correo") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    isError = !correoValido && correo.isNotEmpty()
                )
                if (!correoValido && correo.isNotEmpty()) {
                    Text(
                        text = "Correo electrónico no válido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { registrarUsuarioViewModel.onContrasenaChange(it) },
                    label = { Text("Contraseña") },
                    placeholder = { Text("Ingrese su contraseña") },
                    visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (contrasenaVisible)
                            FontAwesomeIcons.Solid.EyeSlash
                        else
                            FontAwesomeIcons.Solid.Eye

                        IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                            Icon(imageVector = image, contentDescription = null, modifier = Modifier.size(20.dp))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmarContrasena,
                    onValueChange = { registrarUsuarioViewModel.onConfirmarContrasenaChange(it) },
                    label = { Text("Confirmar contraseña") },
                    placeholder = { Text("Ingrese su contraseña nuevamente") },
                    visualTransformation = if (confirmarContrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (confirmarContrasenaVisible)
                            FontAwesomeIcons.Solid.EyeSlash
                        else
                            FontAwesomeIcons.Solid.Eye

                        IconButton(onClick = { confirmarContrasenaVisible = !confirmarContrasenaVisible }) {
                            Icon(imageVector = image, contentDescription = null, modifier = Modifier.size(20.dp))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    isError = !contrasenaValido && confirmarContrasena.isNotEmpty()
                )
                if (!contrasenaValido && confirmarContrasena.isNotEmpty()) {
                    Text(
                        text = "Contraseña no conciden",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = ubigeo,
                    onValueChange = { registrarUsuarioViewModel.onUbigeoChange(it) },
                    label = { Text("Ubigeo") },
                    placeholder = { Text("Ingrese su ubigeo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text("Selecciona tu rol:")
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    roles.forEach { role ->
                        RadioButton(
                            selected = selectedRole == role,
                            onClick = { registrarUsuarioViewModel.onSelectedRoleChange(role) }
                        )
                        Text(role)
                    }
                }

                if (selectedRole == "Especialista") {
                    Spacer(modifier = Modifier.height(16.dp))
                    var expanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectedDropdownItem?.titulo_name ?: "",
                            onValueChange = {},
                            label = { Text("Seleccion su titulo")},
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            colors = OutlinedTextFieldDefaults.colors(),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            dropdownItems.forEach { option: TituloData ->
                                DropdownMenuItem(
                                    text = { Text(text = option.titulo_name) },
                                    onClick = {
                                        expanded = false
                                        registrarUsuarioViewModel.onSelectedDropdownItemChange(option)
                                    }
                                )
                            }
                        }
                    }
                }

                mensajeResult?.let {
                    if (registroValido == false) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Button(
                    onClick = {
                        registrarUsuarioViewModel.registrarUsuario()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "REGISTRAR",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = { navController.navigate(AppScreen.login.route) }) {
                    Text(
                        text = "¿Tiene cuenta? Iniciar Sesión",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
