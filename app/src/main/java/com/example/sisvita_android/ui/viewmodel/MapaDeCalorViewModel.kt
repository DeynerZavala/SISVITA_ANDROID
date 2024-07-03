package com.example.sisvita_android.ui.viewmodel

import android.annotation.SuppressLint
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

    private val _mapaDeCalorResponse = MutableLiveData<MapaDeCalorResponse?>()
    val mapaDeCalorResponse: MutableLiveData<MapaDeCalorResponse?> get() = _mapaDeCalorResponse

    fun setSelectedID(ids: List<Int>) {
        _selectedId.value = ids
        fetchMapaDeCalorData(ids)
    }

    private fun fetchMapaDeCalorData(ids: List<Int>) {
        testRepository.getMapaDeCalor(ids) { response ->
            if (response != null) {
                _mapaDeCalorResponse.postValue(response)

            }
        }
    }
}
