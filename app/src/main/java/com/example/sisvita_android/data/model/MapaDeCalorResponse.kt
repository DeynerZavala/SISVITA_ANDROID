package com.example.sisvita_android.data.model

data class MapaDeCalorResponse (
    val message: String,
    val status : Int,
    val data : ArrayList<MapaDeCalorData>
)
data class MapaDeCalorData(
    val ubigeo: Int,
    val puntuacion: Int,
    val semaforo: String
)