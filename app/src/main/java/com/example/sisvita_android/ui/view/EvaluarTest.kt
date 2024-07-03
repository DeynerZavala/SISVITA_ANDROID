package com.example.sisvita_android.ui.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.ui.viewmodel.EvaluarTestViewModel
import com.example.sisvita_android.utils.UserManager

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluarTest(
    resUserId: Int,
    navController: NavController,
    evaluarTestViewModel: EvaluarTestViewModel = viewModel()
) {
    val evaluarTestResponse by evaluarTestViewModel.dataEstudiante.observeAsState()
    val tratamientosResponse by evaluarTestViewModel.tratamientosResponse.observeAsState()
    val nivelAnsiedadResponse by evaluarTestViewModel.nivelAnsiedadResponse.observeAsState()
    val selectedNivelAnsiedad by evaluarTestViewModel.selectedNivelAnsiedad.observeAsState()
    val selectedTratamiento by evaluarTestViewModel.selectedTratamiento.observeAsState()
    val diagnosticoResponse by evaluarTestViewModel.diagnosticoResponse.observeAsState()
    val errorMessage by evaluarTestViewModel.errorMessage.observeAsState()

    val especialista =
        "${UserManager.getUser()?.nombre} ${UserManager.getUser()?.apellido_paterno} ${UserManager.getUser()?.apellido_materno}"
    val usuario =
        "${evaluarTestResponse?.data?.nombre} ${evaluarTestResponse?.data?.apellido_paterno} ${evaluarTestResponse?.data?.apellido_materno}"
    val test = evaluarTestResponse?.data?.titulo ?: "N/A"
    val fechaTestResuelto = evaluarTestResponse?.data?.fecha_fin ?: "N/A"
    val puntuacion = evaluarTestResponse?.data?.puntuacion ?: "N/A"

    var fundamentacionCientifica by remember { mutableStateOf("") }
    var comunicacionEstudiante by remember { mutableStateOf("") }
    var solicitarCita by remember { mutableStateOf(false) }

    LaunchedEffect(resUserId) {
        evaluarTestViewModel.getData(resUserId)
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            // Por ejemplo, un Toast o un SnackBar
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "EVALUAR TEST",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Especialista: $especialista",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Usuario: $usuario",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Test: $test",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Puntuacion: $puntuacion",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Fecha (Test Resuelto): $fechaTestResuelto",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                var expandedAnsiedad by remember { mutableStateOf(false) }
                var displayTextAnsiedad by remember { mutableStateOf("Selecciona el Nivel de Ansiedad") }

                LaunchedEffect(selectedNivelAnsiedad) {
                    displayTextAnsiedad =
                        if (selectedNivelAnsiedad.isNullOrEmpty()) "Selecciona el Nivel de Ansiedad" else selectedNivelAnsiedad
                            ?: "N/A"
                }

                ExposedDropdownMenuBox(
                    expanded = expandedAnsiedad,
                    onExpandedChange = { expandedAnsiedad = !expandedAnsiedad },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = displayTextAnsiedad,
                        onValueChange = {},
                        label = { Text("Selecciona el Nivel de Ansiedad") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAnsiedad)
                        },
                        colors = OutlinedTextFieldDefaults.colors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAnsiedad,
                        onDismissRequest = { expandedAnsiedad = false }
                    ) {
                        nivelAnsiedadResponse?.data?.map { it.nivel }?.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    expandedAnsiedad = false
                                    evaluarTestViewModel.onSelectedNivelAnsiedadChange(option)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ComboBox Tratamiento
                var expandedTratamiento by remember { mutableStateOf(false) }
                var displayTextTratamiento by remember { mutableStateOf("Selecciona el Tratamiento") }

                LaunchedEffect(selectedTratamiento) {
                    displayTextTratamiento =
                        if (selectedTratamiento.isNullOrEmpty()) "Selecciona el Tratamiento" else selectedTratamiento!!
                }

                ExposedDropdownMenuBox(
                    expanded = expandedTratamiento,
                    onExpandedChange = { expandedTratamiento = !expandedTratamiento },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = displayTextTratamiento,
                        onValueChange = {},
                        label = { Text("Selecciona el Tratamiento") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTratamiento)
                        },
                        colors = OutlinedTextFieldDefaults.colors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTratamiento,
                        onDismissRequest = { expandedTratamiento = false }
                    ) {
                        tratamientosResponse?.data?.map { it.tratamiento_nombre }
                            ?.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        expandedTratamiento = false
                                        evaluarTestViewModel.onSelectedTratamientoChange(option)
                                    }
                                )
                            }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = fundamentacionCientifica,
                    onValueChange = { fundamentacionCientifica = it },
                    label = { Text("Fundamentación Científica") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Comunicación respecto al Estudiante",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = comunicacionEstudiante,
                    onValueChange = { comunicacionEstudiante = it },
                    label = { Text("Comunicación respecto al Estudiante") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Checkbox(
                        checked = solicitarCita,
                        onCheckedChange = { solicitarCita = it },
                        colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        "Solicitar Cita",
                        style = MaterialTheme.typography.titleMedium
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))
                if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            evaluarTestViewModel.guardarDiagnostico(
                                usuarioId = evaluarTestResponse?.data?.usuario_id,
                                especialistaId = UserManager.getUser()?.usuario_id,
                                comunicacionEstudiante = comunicacionEstudiante,
                                solicitarCita = solicitarCita,
                                fundamentacionCientifica = fundamentacionCientifica,
                                res_user_id = evaluarTestResponse?.data?.res_user_id
                            )
                        },
                    ) {
                        Text("Guardar Evaluación")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    defaultLabel: String
) {
    var expanded by remember { mutableStateOf(false) }
    var displayText by remember { mutableStateOf(defaultLabel) }

    LaunchedEffect(selectedOption) {
        displayText = if (selectedOption.isEmpty()) defaultLabel else selectedOption
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            readOnly = true,
            value = displayText,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = false
                        onOptionSelected(option)
                    }
                )
            }
        }
    }

}
