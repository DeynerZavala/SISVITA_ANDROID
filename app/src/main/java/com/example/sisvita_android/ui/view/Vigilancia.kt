package com.example.sisvita_android.ui.view

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.data.model.VigilanciaData
import com.example.sisvita_android.ui.viewmodel.VigilanciaViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vigilancia(
    navController: NavController?,
    vigilanciaViewModel: VigilanciaViewModel = viewModel()
) {
    val vigilanciaList by vigilanciaViewModel.vigilanciaVista.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    var selectedResUserIds by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            vigilanciaViewModel.getVigilancia()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "REALIZAR VIGILANCIA",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
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
                }
                item {
                    TableHeader()
                }
                vigilanciaList?.let { list ->
                    items(list.data) { item ->
                        VigilanciaRow(item, selectedResUserIds) { selectedId ->
                            selectedResUserIds = if (selectedResUserIds.contains(selectedId)) {
                                selectedResUserIds - selectedId
                            } else {
                                selectedResUserIds + selectedId
                            }
                        }
                    }
                }
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
        TableCell("Estado")
        TableCell("Nivel")
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
fun VigilanciaRow(vigilancia: VigilanciaData, selectedResUserIds: Set<Int>, onSelect: (Int) -> Unit) {
    val isSelected = selectedResUserIds.contains(vigilancia.res_user_id)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .clickable { onSelect(vigilancia.res_user_id) }
            .background(if (isSelected) Color.LightGray else Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.padding(2.dp)) {
            TableCell(vigilancia.nombre ?: "N/A")
            TableCell(vigilancia.apellido_paterno ?: "N/A")
            TableCell(vigilancia.apellido_materno ?: "N/A")
            TableCell(vigilancia.fecha_fin ?: "N/A")
            TableCell(vigilancia.puntuacion.toString())
            TableCell(vigilancia.titulo ?: "N/A")
            TableCell(vigilancia.estado ?: "N/A")
        }
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
            label = { Text("Selecciona el estado")},
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