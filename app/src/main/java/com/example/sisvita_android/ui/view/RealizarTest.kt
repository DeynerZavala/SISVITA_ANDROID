package com.example.sisvita_android.ui.view

import android.os.Build
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_and.RealizarTestViewModel
import com.example.sisvita_android.data.model.TestAllPreguntas
import com.example.sisvita_android.data.model.TestResponse
import com.example.sisvita_android.navigation.AppScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealizarTest(
    id: Int,
    navController: NavController,
    realizarTestViewModel: RealizarTestViewModel = viewModel()
) {
    val testResult by realizarTestViewModel.testResult.observeAsState(null)
    val testMensaje by realizarTestViewModel.testMensaje.observeAsState("")
    val context = LocalContext.current
    val testGuardado by realizarTestViewModel.testGuardado.observeAsState(null)
    var showResultDialog by remember { mutableStateOf(false) }

    realizarTestViewModel.getTestById(id)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "REALIZANDO TEST ",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        LazyColumn(contentPadding = it) {
            testResult?.data?.get(0)?.let { testData ->
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = testData.titulo.toString(),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
                items(testData.preguntas) {
                    PreguntasItems(
                        preguntas = it,
                        viewModel = realizarTestViewModel,
                        modifier = Modifier
                    )
                }

                item {
                    if (testMensaje != "Respuesta Guardada") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(text = testMensaje, color = MaterialTheme.colorScheme.error)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                realizarTestViewModel.submitAnswers()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Finalizar test")
                        }
                    }
                }
            }
        }

        if (testGuardado != null && showResultDialog) {
            ResultDialog(
                testGuardado = testGuardado!!,
                onDismiss = {
                    showResultDialog = false
                    realizarTestViewModel.onChangeMensaje("")
                    navController.navigate(AppScreen.testHome.route)
                },
                onConfirm = {
                    showResultDialog = false
                    realizarTestViewModel.onChangeMensaje("")
                    navController.navigate(AppScreen.testHome.route)
                }
            )
        }

        if (testResult == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No hay test")
            }
        }
    }

    LaunchedEffect(testGuardado) {
        if (testGuardado != null) {
            showResultDialog = true
        }
    }
}

@Composable
fun SemaforoIndicator(level: String,semaforo : String) {
    val color = when (semaforo) {
        "Rojo" -> Color.Red
        "Ambar" -> Color.Yellow
        "Verde" -> Color.Green
        else -> Color.Gray
    }


    Text(text = "Nivel de estres: $level", color = color, fontWeight = FontWeight.Bold)

}

@Composable
fun ResultDialog(testGuardado: TestResponse, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Resultados del Test", style = MaterialTheme.typography.headlineMedium)
        },
        text = {
            Column {
                Text(text = "Puntuación: ${testGuardado.puntuacion}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                SemaforoIndicator(level = testGuardado.estado ?: "Desconocido",semaforo= testGuardado.semaforo)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                if (testGuardado.semaforo == "Ambar" || testGuardado.semaforo == "Rojo") {
                    Text("Agendar cita")
                } else {
                    Text("Confirmar")
                }
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun PreguntasItems(
    preguntas: TestAllPreguntas,
    viewModel: RealizarTestViewModel,
    modifier: Modifier = Modifier,
) {
    val selectedOptionId by viewModel.selectedOptions.observeAsState()

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = preguntas.textopregunta, style = MaterialTheme.typography.bodyMedium)
        preguntas.opciones.forEach { opcion ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = opcion.opcion_id == selectedOptionId?.get(preguntas.pregunta_id),
                    onClick = { viewModel.selectOption(preguntas.pregunta_id, opcion.opcion_id) },
                )
                Text(
                    text = opcion.nombre,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}