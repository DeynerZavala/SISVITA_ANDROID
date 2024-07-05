package com.example. sisvita_and

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.TestAllResponse
import com.example.sisvita_android.data.model.TestRequestPregunta
import com.example.sisvita_android.data.model.TestResponse
import com.example.sisvita_android.data.respository.TestRepository
import com.example.sisvita_android.utils.UserManager

class RealizarTestViewModel : ViewModel() {
    private val testRepository = TestRepository()
    private val _testResult = MutableLiveData<TestAllResponse?>()
    val testResult: LiveData<TestAllResponse?> get() = _testResult

    private val _selectedOptions =
        MutableLiveData<Map<Int, Int?>>(mapOf())
    val selectedOptions: LiveData<Map<Int, Int?>> = _selectedOptions

    private val _testGuardado = MutableLiveData<TestResponse?>()
    val testGuardado: LiveData<TestResponse?> get() = _testGuardado

    private val _testMensaje = MutableLiveData("")
    val testMensaje: LiveData<String> get() = _testMensaje
    fun selectOption(questionId: Int, optionId: Int) {
        val updatedMap = _selectedOptions.value?.toMutableMap() ?: mutableMapOf()
        updatedMap[questionId] = optionId
        _selectedOptions.postValue(updatedMap)
    }

    fun getTestById(id: Int) {
        testRepository.getTestById(id) { response ->
            _testResult.postValue(response)
        }
    }

    fun onChangeMensaje(mensaje:String){
        _testMensaje.value = mensaje
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitAnswers() {
        val totalQuestions = testResult.value?.data?.get(0)?.preguntas?.size
        val totalSelected = _selectedOptions.value?.size ?: 0

        if (totalQuestions == totalSelected) {
            println("Respuestas seleccionadas: ${_selectedOptions.value}")
            val preguntasList = ArrayList<TestRequestPregunta>()
            selectedOptions.value?.forEach { (preguntaId, opcionId) ->
                opcionId?.let {
                    preguntasList.add(TestRequestPregunta(pregunta_id = preguntaId, opcion_id = it))
                }

            }
            UserManager.getUser()?.let {
                testRepository.setRespuestaTest(
                    preguntas = preguntasList,
                    usuario_id = it.usuario_id
                ) { response ->
                    _testGuardado.postValue(response)
                    if ((response?.message == "Faltan datos en las preguntas")
                        || (response?.message == "Datos incompletos")
                    ){

                        _testMensaje.postValue("Por favor complete todas preguntas")

                }
                    else if (response?.message  == "Error al guardar respuesta"){
                        _testMensaje.postValue("Error, Intente mas tarde")
                    }
                    else if (response?.message == "Respuesta Guardada"){
                        _testMensaje.postValue("Respuesta Guardada")
                    }
                }
            }

        } else {
            _testMensaje.postValue("No todas las preguntas han sido respondidas.")
        }
    }


}