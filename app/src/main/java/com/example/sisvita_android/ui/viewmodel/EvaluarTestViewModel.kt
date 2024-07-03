package com.example.sisvita_android.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.EvaluarTestDataResponse
import com.example.sisvita_android.data.model.NivelAnsiedadResponse
import com.example.sisvita_android.data.model.RegistrarDiagnosticoResponse
import com.example.sisvita_android.data.model.TratamientosResponse
import com.example.sisvita_android.data.respository.TestRepository
import com.example.sisvita_android.utils.DateUtils

class EvaluarTestViewModel : ViewModel() {

    private val _dataEstudiante = MutableLiveData<EvaluarTestDataResponse?>()
    val dataEstudiante: LiveData<EvaluarTestDataResponse?> get() = _dataEstudiante
    private val testRepository = TestRepository()

    private val _tratamientosResponse = MutableLiveData<TratamientosResponse?>()
    val tratamientosResponse: LiveData<TratamientosResponse?> get() = _tratamientosResponse

    private val _nivelAnsiedadResponse = MutableLiveData<NivelAnsiedadResponse?>()
    val nivelAnsiedadResponse: LiveData<NivelAnsiedadResponse?> get() = _nivelAnsiedadResponse

    private val _selectedNivelAnsiedad = MutableLiveData<String>()
    val selectedNivelAnsiedad: LiveData<String> get() = _selectedNivelAnsiedad

    private val _selectedTratamiento = MutableLiveData<String>()
    val selectedTratamiento: LiveData<String> get() = _selectedTratamiento

    private val _diagnosticoResponse = MutableLiveData<RegistrarDiagnosticoResponse?>()
    val diagnosticoResponse: LiveData<RegistrarDiagnosticoResponse?> get() = _diagnosticoResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(id: Int) {
        testRepository.getVigilanciabyId(id) { response ->
            if (response != null) {
                response.data.fecha_fin = DateUtils.formatDateTime(response.data.fecha_fin) ?: "N/A"
                _dataEstudiante.postValue(response)
            }
        }
        testRepository.getTratamientos { response ->
            if (response != null) {
                _tratamientosResponse.postValue(response)
            }
        }
        testRepository.getNivelAnsiedad { response ->
            if (response != null) {
                _nivelAnsiedadResponse.postValue(response)
            }
        }
    }

    fun onSelectedNivelAnsiedadChange(newNivel: String) {
        _selectedNivelAnsiedad.postValue(newNivel)
        Log.d("EvaluarTestViewModel", "Selected Nivel Ansiedad: $newNivel")
    }

    fun onSelectedTratamientoChange(newTratamiento: String) {
        _selectedTratamiento.postValue(newTratamiento)
        Log.d("EvaluarTestViewModel", "Selected Tratamiento: $newTratamiento")
    }

    fun guardarDiagnostico(
        usuarioId: Int?,
        res_user_id: Int?,
        especialistaId: Int?,
        comunicacionEstudiante: String,
        solicitarCita: Boolean,
        fundamentacionCientifica: String
    ) {
        Log.v("fsdfs", "fsdfsdfsd")
        var comunicacionEstudianteFinal = ""
        val ansiedadId =
            _nivelAnsiedadResponse.value?.data?.find { it.nivel == _selectedNivelAnsiedad.value }?.ansiedad_id
        val tratamientoId =
            _tratamientosResponse.value?.data?.find { it.tratamiento_nombre == _selectedTratamiento.value }?.tratamiento_id

        if (fundamentacionCientifica.isBlank() || fundamentacionCientifica == "") {
            _errorMessage.postValue("Fundamentacion cientifica vacia")
            return
        }
        if (ansiedadId == null || tratamientoId == null || usuarioId == null
            || especialistaId == null || res_user_id == null
        ) {
            _errorMessage.postValue("Datos incompletos")
            return
        }
        if (comunicacionEstudiante.isNotBlank()) {
            comunicacionEstudianteFinal = comunicacionEstudiante
        }
        testRepository.setDiagnostico(
            usuario_id = usuarioId,
            especialista_id = especialistaId,
            ansiedad_id = ansiedadId,
            comunicacion_estudiante = comunicacionEstudiante,
            solicitar_cita = solicitarCita,
            tratamiento_id = tratamientoId,
            res_user_id = res_user_id,
            fundamentacion_cientifica = fundamentacionCientifica
        ) { response ->
            _diagnosticoResponse.postValue(response)
            _errorMessage.postValue(response?.message)
        }
    }
}
