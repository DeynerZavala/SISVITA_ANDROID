package com.example.sisvita_android.data.model

data class LoginResponse(
    val message: String,
    val status: Int,
    val rol : String,
    val data: Usuario
)