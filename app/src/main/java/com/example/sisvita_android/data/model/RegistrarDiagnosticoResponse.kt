package com.example.sisvita_android.data.model

data class RegistrarDiagnosticoResponse (
    val message: String,
    val status: Int,
    val data: RegistrarDisagnosticoData
)
data class RegistrarDisagnosticoData(
    val diagnostico_id: Int,
    val especialista_id:Int,
    val fecha:String,
    val comunicacion_estudiante:String,
    val solicitar_cita:Boolean,
    val tratamiento_id:Int,
    val fundamentacion_cientifica:String

)