package com.example.sisvita_android.network

import com.example.sisvita_android.data.model.LoginRequest
import com.example.sisvita_android.data.model.LoginResponse
import com.example.sisvita_android.data.model.MapaDeCalorRequest
import com.example.sisvita_android.data.model.MapaDeCalorResponse
import com.example.sisvita_android.data.model.RegistrarEspecialistaRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioRequest
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.data.model.TestAllResponse
import com.example.sisvita_android.data.model.TestListResponse
import com.example.sisvita_android.data.model.TestRequest
import com.example.sisvita_android.data.model.TestResponse
import com.example.sisvita_android.data.model.TituloResponse
import com.example.sisvita_android.data.model.UsuarioResponse
import com.example.sisvita_android.data.model.VigilanciaResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("hello")
    fun hello():Call<Void>
    @POST("usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @POST("usuarios")
    fun registrarUsuario(@Body registrarUsuarioRequest: RegistrarUsuarioRequest): Call<RegistrarUsuarioResponse>
    @POST("especialistas")
    fun registrarEspecialista(@Body registrarEspecialistaRequest: RegistrarEspecialistaRequest): Call<RegistrarUsuarioResponse>
    @GET("test")
    fun getTests():Call<TestListResponse>
    @GET("titulo")
    fun getTitulos():Call<TituloResponse>
    @GET("test/all/{testId}")
    fun getTestById(@Path("testId") testId: Int): Call<TestAllResponse>
    @GET("usuarios/{usuario_id}")
    fun getUsuario(@Path("usuario_id") usuario_id:Int): Call<UsuarioResponse>
    @POST("test/responder")
    fun setRespuestaTest(@Body testRequest: TestRequest): Call<TestResponse>
    @GET("test/vigilancia")
    fun getVigilancia(): Call<VigilanciaResponse>
    @POST("test/mapadecalor")
    fun getMapaDeCalor(@Body mapaDeCalorRequest: MapaDeCalorRequest): Call<MapaDeCalorResponse>
}
