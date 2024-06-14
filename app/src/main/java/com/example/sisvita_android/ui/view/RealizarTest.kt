package com.example.sisvita_android.ui.view

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
                    containerColor =
                    MaterialTheme.colorScheme.primary
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
                                if( testMensaje == "Respuesta Guardada"){
                                    realizarTestViewModel.onChangeMensaje("")
                                    Toast.makeText(context,"RespuestGuardada",Toast.LENGTH_LONG).show()
                                    navController.navigate(AppScreen.testHome.route)

                                }
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

