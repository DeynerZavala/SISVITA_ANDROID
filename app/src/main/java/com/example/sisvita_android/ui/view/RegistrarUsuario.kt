package com.example.sisvita_android.ui.view


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import compose.icons.fontawesomeicons.solid.EyeSlash
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrarUsuario(
    navController: NavController,
    registrarUsuarioViewModel: RegistrarUsuarioViewModel = viewModel()
) {
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


    val departamentos by registrarUsuarioViewModel.departamentos.observeAsState(emptyList())
    val provincias by registrarUsuarioViewModel.provincias.observeAsState(emptyList())
    val distritos by registrarUsuarioViewModel.distritos.observeAsState(emptyList())


    val selectedDepartamento by registrarUsuarioViewModel.departamento.observeAsState("")
    val selectedProvincia by registrarUsuarioViewModel.provincia.observeAsState("")
    val selectedDistrito by registrarUsuarioViewModel.distrito.observeAsState("")




    val dropdownItems by registrarUsuarioViewModel.dropdownItems.observeAsState(emptyList())
    val selectedDropdownItem by registrarUsuarioViewModel.selectedDropdownItem.observeAsState()


    LaunchedEffect(Unit) {
        launch {
            registrarUsuarioViewModel.iniciarTitulo()
        }
    }
    LaunchedEffect(registroValido) {
        if (registroValido == true) {
            Toast.makeText(context,
                context.getString(R.string.usuario_registrado_exitosamente), Toast.LENGTH_SHORT).show()
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
            Icon(
                imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
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
                Text(
                    stringResource(R.string.sisvita),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { registrarUsuarioViewModel.onNombreChange(it) },
                    label = { Text(stringResource(R.string.nombre)) },
                    placeholder = { Text(stringResource(R.string.ingrese_su_nombre)) },
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
                    label = { Text(stringResource(R.string.apellido_paterno)) },
                    placeholder = { Text(stringResource(R.string.ingrese_su_apellido_paterno)) },
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
                    label = { Text(stringResource(R.string.apellido_materno)) },
                    placeholder = { Text(stringResource(R.string.ingrese_su_apellido_materno)) },
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
                    label = { Text(stringResource(R.string.correo)) },
                    placeholder = { Text(stringResource(R.string.ingrese_su_correo)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    isError = !correoValido && correo.isNotEmpty()
                )
                if (!correoValido && correo.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.correo_electr_nico_no_v_lido),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { registrarUsuarioViewModel.onContrasenaChange(it) },
                    label = { Text(stringResource(R.string.contrase_a)) },
                    placeholder = { Text(stringResource(R.string.ingrese_su_contrase_a)) },
                    visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (contrasenaVisible)
                            FontAwesomeIcons.Solid.EyeSlash
                        else
                            FontAwesomeIcons.Solid.Eye


                        IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
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
                    label = { Text(stringResource(R.string.confirmar_contrase_a)) },
                    placeholder = { Text(stringResource(R.string.ingrese_su_contrase_a_nuevamente)) },
                    visualTransformation = if (confirmarContrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (confirmarContrasenaVisible)
                            FontAwesomeIcons.Solid.EyeSlash
                        else
                            FontAwesomeIcons.Solid.Eye


                        IconButton(onClick = {
                            confirmarContrasenaVisible = !confirmarContrasenaVisible
                        }) {
                            Icon(
                                imageVector = image,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
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
                        text = stringResource(R.string.contrase_a_no_conciden),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))




                // Combobox for Departamento
                ExposedDropdownMenuBox(
                    expanded = registrarUsuarioViewModel.departamentoMenuExpanded.observeAsState(
                        false
                    ).value,
                    onExpandedChange = { registrarUsuarioViewModel.onDepartamentoMenuExpandedChange() }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedDepartamento,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.departamento)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = registrarUsuarioViewModel.departamentoMenuExpanded.observeAsState(
                                    false
                                ).value
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = registrarUsuarioViewModel.departamentoMenuExpanded.observeAsState(
                            false
                        ).value,
                        onDismissRequest = { registrarUsuarioViewModel.onDepartamentoMenuExpandedChange() }
                    ) {
                        departamentos.forEach { departamento ->
                            DropdownMenuItem(
                                text = { Text(departamento) },
                                onClick = {
                                    registrarUsuarioViewModel.onDepartamentoChange(departamento)
                                    registrarUsuarioViewModel.onDepartamentoMenuExpandedChange()
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))




// Combobox for Provincia
                ExposedDropdownMenuBox(
                    expanded = registrarUsuarioViewModel.provinciaMenuExpanded.observeAsState(false).value,
                    onExpandedChange = { registrarUsuarioViewModel.onProvinciaMenuExpandedChange() }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedProvincia,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.provincia)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = registrarUsuarioViewModel.provinciaMenuExpanded.observeAsState(
                                    false
                                ).value
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = registrarUsuarioViewModel.provinciaMenuExpanded.observeAsState(
                            false
                        ).value,
                        onDismissRequest = { registrarUsuarioViewModel.onProvinciaMenuExpandedChange() }
                    ) {
                        provincias.forEach { provincia ->
                            DropdownMenuItem(
                                text = { Text(provincia) },
                                onClick = {
                                    registrarUsuarioViewModel.onProvinciaChange(provincia)
                                    registrarUsuarioViewModel.onProvinciaMenuExpandedChange()
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))




// Combobox for Distrito
                ExposedDropdownMenuBox(
                    expanded = registrarUsuarioViewModel.distritoMenuExpanded.observeAsState(false).value,
                    onExpandedChange = { registrarUsuarioViewModel.onDistritoMenuExpandedChange() }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedDistrito,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.distrito)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = registrarUsuarioViewModel.distritoMenuExpanded.observeAsState(
                                    false
                                ).value
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = registrarUsuarioViewModel.distritoMenuExpanded.observeAsState(
                            false
                        ).value,
                        onDismissRequest = { registrarUsuarioViewModel.onDistritoMenuExpandedChange() }
                    ) {
                        distritos.forEach { distrito ->
                            DropdownMenuItem(
                                text = { Text(distrito) },
                                onClick = {
                                    registrarUsuarioViewModel.onDistritoChange(distrito)
                                    registrarUsuarioViewModel.onDistritoMenuExpandedChange()
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))


                Text(stringResource(R.string.selecciona_tu_rol))
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


                if (selectedRole == stringResource(R.string.especialista)) {
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
                            label = { Text(stringResource(R.string.seleccion_su_titulo)) },
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
                                        registrarUsuarioViewModel.onSelectedDropdownItemChange(
                                            option
                                        )
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
                        text = stringResource(R.string.registrar),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = { navController.navigate(AppScreen.login.route) }) {
                    Text(
                        text = stringResource(R.string.tiene_cuenta_iniciar_sesi_n),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

