package com.example.sisvita_android.data.model

data class RegistrarDiagnosticoRequest(
    val usuario_id: Int,
    val res_user_id:Int,
    val especialista_id: Int,
    val ansiedad_id: Int,
    val comunicacion_estudiante:String,
    val solicitar_cita: Boolean,
    val tratamiento_id: Int,
    val fundamentacion_cientifica: String
)