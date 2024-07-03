package com.example.sisvita_android.data.model

data class MapaDeCalorResponse (
    val message: String,
    val status : Int,
    val data : ArrayList<MapaDeCalorData>
)
data class MapaDeCalorData(
    val res_user_id:Int,
    val ubigeo: Int,
    val puntuacion: Int,
    val nivel_semaforo: String,
    val longitud: Double,
    val latitud: Double,
    val maximo: Int
)