package com.example.sisvita_android.network

import com.example.sisvita_android.data.model.LoginRequest
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.data.model.RegistrarUsuarioRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.data.model.TestAllResponse
import com.example.sisvita_android.data.model.TestListResponse
import com.example.sisvita_android.data.model.TestRequest
import com.example.sisvita_android.data.model.TestResponse
import com.example.sisvita_android.data.model.UsuarioResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @POST("usuarios")
    fun registrarUsuario(@Body registrarUsuarioRequest: RegistrarUsuarioRequest): Call<RegistrarUsuarioResponse>
    @GET("test")
    fun getTests():Call<TestListResponse>
    @GET("test/all/{testId}")
    fun getTestById(@Path("testId") testId: Int): Call<TestAllResponse>
    @GET("usuarios/{usuario_id}")
    fun getUsuario(@Path("usuario_id") usuario_id:Int): Call<UsuarioResponse>
    @POST("test/responder")
    fun setRespuestaTest(@Body testRequest: TestRequest): Call<TestResponse>
}
