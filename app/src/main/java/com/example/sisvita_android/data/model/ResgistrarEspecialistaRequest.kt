package com.example.sisvita_android.data.model

data class RegistrarEspecialistaRequest(
    val nombre: String,
    val apellido_paterno: String,
    val apellido_materno: String,
    val correo_electronico: String,
    val contrasena: String,
    val ubigeo: Int,
    val titulo_id: Int
)