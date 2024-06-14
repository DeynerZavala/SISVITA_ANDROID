package com.example.sisvita_android.network

import com.example.sisvita_android.data.model.LoginRequest
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.data.model.RegistrarUsuarioRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.data.model.TestListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @POST("usuarios")
    fun registrarUsuario(@Body registrarUsuarioRequest: RegistrarUsuarioRequest): Call<RegistrarUsuarioResponse>
    @GET("test")
    fun getTests():Call<TestListResponse>

}
