package com.example.sisvita_android.ui.view

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.R
import com.example.sisvita_android.data.model.VigilanciaData
import com.example.sisvita_android.ui.viewmodel.VigilanciaViewModel
import com.google.gson.Gson
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vigilancia(
    navController: NavController,
    vigilanciaViewModel: VigilanciaViewModel = viewModel()
) {
    val vigilanciaResponse by vigilanciaViewModel.vigilanciaVista.observeAsState()
    val vigilanciaList = vigilanciaResponse?.data ?: emptyList()
    val selectedResUserIds by vigilanciaViewModel.selectedResUserIds.observeAsState(emptySet())
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vigilanciaViewModel.getVigilancia()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.error)) },
            text = { Text(stringResource(R.string.seleccione_al_menos_un_elemento)) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.realizar_vigilancia),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (selectedResUserIds.isNotEmpty()) {
                        val resUserIdsString = selectedResUserIds.joinToString(",")
                        navController.navigate("mapaDeCalor/$resUserIdsString")
                    } else {
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.enviar_selecci_n), color = Color.White)
            }
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Periodo()
                TestTipo()
                TestNivel()
                SelectAllToggle(vigilanciaList, selectedResUserIds.size == vigilanciaList.size) {
                    if (selectedResUserIds.size == vigilanciaList.size) {
                        vigilanciaViewModel.deselectAll()
                    } else {
                        vigilanciaViewModel.selectAll(vigilanciaList)
                    }
                }
            }
            item {
                TableHeader()
            }
            items(vigilanciaList) { item ->
                VigilanciaRow(item, selectedResUserIds,navController) { selectedId ->
                    vigilanciaViewModel.toggleSelection(selectedId)
                }
            }
        }
    }
}


@Composable
fun SelectAllToggle(vigilanciaList: List<VigilanciaData>, allSelected: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = onToggle) {
            Text(text = if (allSelected) "Deseleccionar Todo" else "Seleccionar Todo")
        }
    }
}



@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .height(45.dp)
            .padding(vertical = 1.dp)
    ) {
        TableCell("Nombre")
        TableCell("Apellido Paterno")
        TableCell("Apellido Materno")
        TableCell("Fecha Fin")
        TableCell("Puntuacion")
        TableCell("Titulo")
        TableCell("Nivel")
        TableCell("Mas")
    }
}

@Composable
fun TableCell(text: String) {
    Box(
        modifier = Modifier
            .width(45.dp)
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VigilanciaRow(
    vigilancia: VigilanciaData,
    selectedResUserIds: Set<Int>,
    navController: NavController,
    onSelect: (Int) -> Unit
) {
    val isSelected = selectedResUserIds.contains(vigilancia.res_user_id)
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .clickable { onSelect(vigilancia.res_user_id) }
            .background(if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TableCell(vigilancia.nombre ?: "N/A")
            TableCell(vigilancia.apellido_paterno ?: "N/A")
            TableCell(vigilancia.apellido_materno ?: "N/A")
            TableCell(vigilancia.fecha_fin ?: "N/A")
            TableCell(vigilancia.puntuacion.toString())
            TableCell(vigilancia.titulo ?: "N/A")
            CircleTableCell(vigilancia.semaforo_nivel ?: "N/A")
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Ver más") },
                        onClick = { showDialog = true }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Evaluar Test") },
                        onClick = {
                            expanded = false
                            navController.navigate("evaluarTest/${vigilancia.res_user_id}")
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        VigilanciaDetailDialog(
            vigilancia = vigilancia,
            onDismiss = { showDialog = false }
        )
    }
}


fun VigilanciaData.toJson(): String {
    return Gson().toJson(this)
}

@Composable
fun VigilanciaDetailDialog(vigilancia: VigilanciaData, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Detalles de Vigilancia", style = MaterialTheme.typography.headlineMedium)
        },
        text = {
            Column {
                Text(text = "Nombre: ${vigilancia.nombre?:"N/A"}")
                Text(text = "Apellido Paterno: ${vigilancia.apellido_paterno?:"N/A"}")
                Text(text = "Apellido Materno: ${vigilancia.apellido_materno?:"N/A"}")
                Text(text = "Fecha Fin: ${vigilancia.fecha_fin?:"N/A"}")
                Text(text = "Puntuacion: ${vigilancia.puntuacion?:"N/A"}")
                Text(text = "Titulo: ${vigilancia.titulo?:"N/A"}")
                Text(text = "Nivel del Test: ${vigilancia.test_nivel?:"N/A"}")
                Text(text = "Nivel de Ansiedad del diagnostico : ${vigilancia.diag_ansiedad_nivel?:"N/A"}")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Nivel de Ansiedad : ")
                    CircleTableCell(vigilancia.semaforo_nivel ?: "N/A")
                }


            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}
@Composable
fun CircleTableCell(nivel: String) {
    Box(
        modifier = Modifier
            .width(45.dp)
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        DrawCircle(nivel = nivel, radius = 30.0f)
    }
}

@Composable
fun DrawCircle(nivel: String, radius: Float) {
    val color = when (nivel) {
        "Rojo" -> Color.Red
        "Ambar" -> Color.Yellow
        "Verde" -> Color.Green
        else -> Color.Gray
    }
    Canvas(
        modifier = Modifier
            .size((2 * radius).dp)
            .padding(4.dp)
    ) {
        drawCircle(
            color = color,
            radius = radius
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestTipo (vigilanciaViewModel: VigilanciaViewModel = viewModel()){
    val testTipo by vigilanciaViewModel.testTipo.observeAsState(emptyList())
    val selectedTipo by vigilanciaViewModel.selectedTestTipo.observeAsState(null)

    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedTipo?:"",
            onValueChange = {},
            label = { Text("Selecciona el tipo de test")},
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
            testTipo.forEach { Tipo ->
                DropdownMenuItem(
                    text = { Text(text = Tipo) },
                    onClick = {
                        expanded = false
                        vigilanciaViewModel.onSelectedTestTipo(Tipo)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestNivel (vigilanciaViewModel: VigilanciaViewModel = viewModel()){
    val testNivel by vigilanciaViewModel.testNivel.observeAsState(emptyList())
    val selectedTNivel by vigilanciaViewModel.selectedTestNivel.observeAsState(null)

    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedTNivel?:"",
            onValueChange = {},
            label = { Text("Selecciona el nivel")},
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
            testNivel.forEach { nivel ->
                DropdownMenuItem(
                    text = { Text(text = nivel) },
                    onClick = {
                        expanded = false
                        vigilanciaViewModel.onSelectedTestNivel(nivel)
                    }
                )
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Periodo(vigilanciaViewModel: VigilanciaViewModel = viewModel()) {
    val fechaInicio by vigilanciaViewModel.fechaInicio.observeAsState()
    val fechaFin by vigilanciaViewModel.fechaFin.observeAsState()

    Column {
        Text(text = "Periodo")
        Text(text = "Fecha Inicio:")
        DatePickerField(
            selectedDate = fechaInicio,
            onDateSelected = { selectedDate ->
                vigilanciaViewModel.onFechaInicioSelected(selectedDate)
            }
        )
        Text(text = "Fecha Fin:")
        DatePickerField(
            selectedDate = fechaFin,
            onDateSelected = vigilanciaViewModel::onFechaFinSelected
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerField(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    Box(contentAlignment = Alignment.Center) {
        Button(onClick = { showDatePicker = true }) {
            Text(text = selectedDate?.let { dateFormat.format(Date.from(it.atStartOfDay().atZone(
                ZoneId.systemDefault()).toInstant())) } ?: "Seleccionar la Fecha")
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        val onDateChange: (LocalDate) -> Unit = { date ->
            showDatePicker = false
            onDateSelected(date)
        }

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis ?: return@Button
                    val selectedDate = LocalDate.ofEpochDay(selectedMillis / (24 * 60 * 60 * 1000))
                    onDateChange(selectedDate)
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text(text = "Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}