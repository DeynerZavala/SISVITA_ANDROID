package com.example.sisvita_android.data.model

data class VigilanciaResponse(
    val data: ArrayList<VigilanciaData>,
    val message: String,
    val status: Int
)

data class VigilanciaData(
    val nombre: String,
    val apellido_paterno: String,
    val apellido_materno: String,
    val res_user_id: Int,
    var fecha_fin: String,
    val puntuacion: Int,
    val test_id: Int,
    val titulo: String,
    val estado: String,
    val diagnostico_id: Int?,
    val ansiedad_id: Int?,
    val nivel: String?
)
