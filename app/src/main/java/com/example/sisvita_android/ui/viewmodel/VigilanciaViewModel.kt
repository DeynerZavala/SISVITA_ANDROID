package com.example.sisvita_android.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.VigilanciaData
import com.example.sisvita_android.data.model.VigilanciaResponse
import com.example.sisvita_android.data.respository.TestRepository
import com.example.sisvita_android.utils.DateUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class VigilanciaViewModel : ViewModel() {
    private val testRepository = TestRepository()
    private val _vigilanciaResponse = MutableLiveData<VigilanciaResponse?>()
    val vigilanciaResponse: LiveData<VigilanciaResponse?> get() = _vigilanciaResponse

    private val _vigilanciaVista = MutableLiveData<VigilanciaResponse?>()
    val vigilanciaVista: LiveData<VigilanciaResponse?> get() = _vigilanciaVista

    private val _testTipo = MutableLiveData<ArrayList<String>>(arrayListOf("Ninguno"))
    val testTipo: LiveData<ArrayList<String>> get() = _testTipo

    private val _selectedTestTipo = MutableLiveData<String>(null)
    val selectedTestTipo: LiveData<String> get() = _selectedTestTipo

    private val _testNivel = MutableLiveData<ArrayList<String>>(arrayListOf("Ninguno"))
    val testNivel: LiveData<ArrayList<String>> get() = _testNivel

    private val _selectedTestNivel = MutableLiveData<String>(null)
    val selectedTestNivel: LiveData<String> get() = _selectedTestNivel

    private val _fechaInicio = MutableLiveData<LocalDate?>(null)
    val fechaInicio: LiveData<LocalDate?> get() = _fechaInicio

    @RequiresApi(Build.VERSION_CODES.O)
    private val _fechaFin = MutableLiveData<LocalDate?>(LocalDate.now())
    val fechaFin: LiveData<LocalDate?> @RequiresApi(Build.VERSION_CODES.O)
    get() = _fechaFin

    private val _selectedResUserIds = MutableLiveData<Set<Int>>(emptySet())
    val selectedResUserIds: LiveData<Set<Int>> get() = _selectedResUserIds

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSelectedTestTipo(titulo: String) {
        _selectedTestTipo.value = titulo
        filterVigilancia()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSelectedTestNivel(nivel: String) {
        _selectedTestNivel.value = nivel
        filterVigilancia()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFechaInicioSelected(fecha: LocalDate) {
        _fechaInicio.value = fecha
        validateDates()
        filterVigilancia()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFechaFinSelected(fecha: LocalDate) {
        _fechaFin.value = fecha
        validateDates()
        filterVigilancia()
    }
    fun selectAll(vigilanciaList: List<VigilanciaData>) {
        _selectedResUserIds.value = vigilanciaList.map { it.res_user_id }.toSet()
    }

    fun deselectAll() {
        _selectedResUserIds.value = emptySet()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getVigilancia() {
        testRepository.getVigilancia { Response ->
            if (Response != null) {
                for (data in Response.data!!) {
                    data.fecha_fin = DateUtils.formatDateTime(data.fecha_fin)
                }
                _vigilanciaResponse.postValue(Response)
                _vigilanciaVista.postValue(Response)

            }
        }
        testRepository.getTests { Response ->
            if (Response != null) {
                for (data in Response.data) {
                    _testTipo.postValue(ArrayList((_testTipo.value ?: arrayListOf()).apply { add(data.titulo) }))
                }
            }
        }
        testRepository.getAnsiedadSemaforo {
            Response->
            if (Response != null) {
                for(data in Response.data){
                    _testNivel.postValue(ArrayList((_testNivel.value ?: arrayListOf()).apply { add(data.semaforo) }))
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterVigilancia() {
        val originalList = _vigilanciaResponse.value?.data ?: return
        val filteredList = originalList.filter { data ->
            val matchesTipo = _selectedTestTipo.value.isNullOrEmpty() || _selectedTestTipo.value == "Ninguno" || data.titulo == _selectedTestTipo.value
            val matchesNivel = _selectedTestNivel.value.isNullOrEmpty() || _selectedTestNivel.value == "Ninguno" || data.semaforo_nivel == _selectedTestNivel.value
            val matchesPeriodo = (_fechaInicio.value == null || parseDate(data.fecha_fin)?.isAfter(_fechaInicio.value?.minusDays(1)) == true) &&
                    (_fechaFin.value == null || parseDate(data.fecha_fin)?.isBefore(_fechaFin.value?.plusDays(1)) == true)
            matchesTipo && matchesNivel && matchesPeriodo
        }

        if (_selectedTestTipo.value.isNullOrEmpty() && _selectedTestNivel.value.isNullOrEmpty() && _fechaInicio.value == null && _fechaFin.value == null) {
            _vigilanciaVista.postValue(_vigilanciaResponse.value)
        } else {
            _vigilanciaVista.postValue(VigilanciaResponse(ArrayList(filteredList), _vigilanciaResponse.value?.message ?: "", _vigilanciaResponse.value?.status ?: 0))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateDates() {
        _fechaInicio.value?.let { inicio ->
            _fechaFin.value?.let { fin ->
                if (fin.isBefore(inicio)) {
                    _fechaFin.value = inicio
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDate(dateString: String): LocalDate? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            LocalDate.parse(dateString, formatter)
        } catch (e: Exception) {
            null
        }
    }

    fun toggleSelection(resUserId: Int) {
        val currentSelection = _selectedResUserIds.value.orEmpty()
        _selectedResUserIds.value = if (currentSelection.contains(resUserId)) {
            currentSelection - resUserId
        } else {
            currentSelection + resUserId
        }
        Log.v("SELECCIONAR CELDA : ",_selectedResUserIds.value.toString())
    }
}
