package com.example.sisvita_android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.MapaDeCalorResponse
import com.example.sisvita_android.data.respository.TestRepository

class MapaDeCalorViewModel : ViewModel() {
    private val testRepository = TestRepository()
    private val _selectedId = MutableLiveData<List<Int>>(emptyList())
    val selectedID: LiveData<List<Int>> get() = _selectedId

    private val _mapaDeCalorResponse = MutableLiveData<MapaDeCalorResponse>()
    val mapadeCalorResponse : LiveData<MapaDeCalorResponse> get() = _mapaDeCalorResponse

    fun setSelectedID(temp: List<Int>) {
        _selectedId.value = temp
        testRepository.getMapaDeCalor(_selectedId.value) { response ->
        }
    }
}
