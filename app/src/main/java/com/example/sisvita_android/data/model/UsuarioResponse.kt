package com.example.sisvita_android.data.model

data class UsuarioResponse(
    val data: Usuario,
    val message : String,
    val status: Int,
)
data class Usuario(
    val usuario_id: Int,
    val nombre: String,
    val fecha_registro: String,
    val correo_electronico :String,
    val apellido_paterno : String,
    val apellido_materno : String,
)
